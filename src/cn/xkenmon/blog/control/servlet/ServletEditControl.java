package cn.xkenmon.blog.control.servlet;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.DAOFactory;
import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.DAO.impl.ArticlePicDAOImpl;
import cn.xkenmon.blog.DAO.impl.UserDAOProxy;
import cn.xkenmon.blog.Throwable.FileFormatMistakenException;
import cn.xkenmon.blog.Throwable.FileSizeTooLargeException;
import cn.xkenmon.blog.util.ArticleDeleteUtil;
import cn.xkenmon.blog.util.ImgUploadUtil;
import cn.xkenmon.blog.vo.Article;
import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/manage")
public class ServletEditControl {
    private static Logger logger = LoggerFactory.getLogger(ServletEditControl.class);
    private static final String IMG_PATH = "/userInfo/pic/ArticlePic/";

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id_str, @SessionAttribute User currentUser) {
        ArticlesDAO dao = DAOFactory.getInstance();
        int id = Integer.valueOf(id_str);

        Article article = dao.findById(id);
        if (article.getAuthor().equals(currentUser.getUserName())) {
            ArticleDeleteUtil.delete(IMG_PATH, article);
            dao.deleteById(id);
            logger.info("删除文章：" + article.getTitle() + "--id:" + article.getId());
        }
        dao.close();
        return "redirect:/index.html";
    }


    @RequestMapping({"/edit/{id}", "/edit"})
    public String editControl(HttpSession session, HttpServletRequest request, @PathVariable(value = "id", required = false) String id_str) {
        ArticlesDAO dao = DAOFactory.getInstance();
        List<String> typeList = dao.getTypeList();
        request.setAttribute("typeList", typeList);


        if (id_str != null && !id_str.equals("")) {
            User user = (User) session.getAttribute("currentUser");

            Article article = dao.findById(Integer.valueOf(id_str));
            if (user.getUserName().equals(article.getAuthor())) {
                request.setAttribute("article", article);
            }
        }
        //先清空session中的uploadPic属性
        if (session.getAttribute("uploadPic") != null)
            session.removeAttribute("uploadPic");

        dao.close();
        return "postedit";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String addArticle(@ModelAttribute("article") Article article, @RequestParam(value = "id", required = false) String id_str, @RequestParam("coverImg") CommonsMultipartFile coverImg, HttpSession session, HttpServletResponse response) {
        User currentUser = (User) session.getAttribute("currentUser");
        ArticlesDAO dao = DAOFactory.getInstance();
        int id = 0;

        //判断用户是更新文章还是添加文章
        boolean isUpdate = false;

        if (id_str != null && !id_str.equals(""))
            isUpdate = true;
        if (isUpdate) {
            id = Integer.valueOf(id_str);
            Article old = dao.findById(id);

            article.setId(id);
            article.setDate(old.getDate());
            article.setUpdateTime(new Date());
            article.setCover(old.getCover());
        } else {
            article.setReadCount(0);
            article.setDate(new Date());
            article.setUpdateTime(article.getDate());

        }
        article.setAuthor(currentUser.getUserName());
        if (!coverImg.isEmpty()) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename;
            try {
                filename = ImgUploadUtil.upload(coverImg, IMG_PATH, 5000);
            } catch (FileSizeTooLargeException e) {
                e.printStackTrace();
                assert out != null;
                out.println("<script>");
                out.println("alert(\"文件过大\");");
                out.println("</script>");
                out.close();
                return null;
            } catch (FileFormatMistakenException e) {
                e.printStackTrace();
                assert out != null;
                out.println("<script>");
                out.println("alert(\"文件类型不匹配(必须为.jpg/.gif/.bmp/.png文件)\");");
                out.println("</script>");
                out.close();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                assert out != null;
                out.println("<script>");
                out.println("alert(\"服务器异常！无法上传文件/(ㄒoㄒ)/~~");
                out.println("</script>");
                out.close();
                return null;
            }
            article.setCover(filename);
        }

        if (!isUpdate) {
            dao.createArticle(article);
            List<Article> articleList = dao.findByTitle(article.getTitle());
            if (articleList.size() > 1)
                article = articleList.get(articleList.size() - 1);
            else
                article = articleList.get(0);
        } else {
            dao.updateById(id, article);
        }

        ArrayList picList = (ArrayList) session.getAttribute("uploadPic");
        if (picList != null) {
            ArticlePicDAOImpl picDAO = new ArticlePicDAOImpl();

            for (Object pic : picList) {
                picDAO.insertPic(pic.toString(), article.getId());
                logger.info(currentUser.getUserName() + ":添加图片信息--" + pic.toString() + " 文章标题：" + article.getTitle());
            }
            picDAO.close();
        }


        dao.close();
        logger.info(currentUser.getUserName() + ": Add Article:" + article.toString());
        return "redirect:/Article/" + article.getId();
    }


    //ckEditor图片上传处理器
    @RequestMapping(value = "/UserArticleFileUpload.do", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("upload") CommonsMultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = (User) request.getSession().getAttribute("currentUser");
        logger.info(user.getUserName() + "--准备上传文件:" + upload.getName());

        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String fileName;
        try {
            fileName = ImgUploadUtil.upload(upload, IMG_PATH, 5000);
//            在Session层面上设置文章添加图片的名称
            HttpSession session = request.getSession();
            ArrayList list = (ArrayList) session.getAttribute("uploadPic");
            if (list == null) {
                list = new ArrayList();
            }
            list.add(fileName);
            session.setAttribute("uploadPic", list);
            logger.info("为上传的图片设置session属性：" + list.toString());
        } catch (FileSizeTooLargeException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                    + ",''," + "'文件大小不得大于5000k');");
            out.println("</script>");
            out.close();
            logger.info("文件过大");
            return null;

        } catch (FileFormatMistakenException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                    + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
            out.println("</script>");
            logger.info("文件格式错误！");
            out.close();
            return null;
        }
        // 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名
        assert out != null;
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                + ",'" + "http://119.28.7.42:8080/userImg/ArticlePic?fileName=" + fileName + "','')");
        out.println("</script>");
        logger.info("返回\"图像\"选项卡并显示图片");
        out.close();
        return null;
    }

    @RequestMapping(value = "/registerCheck", method = RequestMethod.POST)
    public String userCheck(HttpServletResponse response,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "email", required = false) String email) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            UserDAO dao = new UserDAOProxy();

            logger.info("name:" + name);
            logger.info("email:" + email);

            String msg = null;
            if (name != null) {
                logger.info("-----------:befor:" + name);
                name = URLDecoder.decode(name, "utf-8");
                logger.info("----------------------:" + name);
                User user = dao.findUserByName(name);
                if (user == null) {
                    msg = "可以注册哦";
                } else {
                    msg = "该用户名已被注册";
                    logger.info(user.toString());
                }
            }

            if (email != null) {
                logger.info("-----------:befor:" + email);
                email = URLDecoder.decode(email, "utf-8");
                logger.info("----------------------:" + email);
                User user = dao.findUserByEmail(email);
                if (user == null) {
                    msg = "可以注册哦";
                } else {
                    msg = "该邮箱已被注册";
                    logger.info(user.toString());
                }
            }


            if (msg != null) {
                out.write(msg);
                logger.info(msg);
            }
            out.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
