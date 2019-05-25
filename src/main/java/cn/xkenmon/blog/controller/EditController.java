package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.Throwable.FileFormatMistakenException;
import cn.xkenmon.blog.Throwable.FileSizeTooLargeException;
import cn.xkenmon.blog.service.IArticlePicService;
import cn.xkenmon.blog.service.IArticleService;
import cn.xkenmon.blog.util.Config;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/manage")
public class EditController {

    final private
    IArticleService articleService;

    final private
    IArticlePicService picService;

    private static Logger logger = LoggerFactory.getLogger(EditController.class);

    public EditController(IArticleService articleService, IArticlePicService picService) {
        this.articleService = articleService;
        this.picService = picService;
    }
//    private static final String IMG_PATH = Config.get("picPath");

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") int id, @SessionAttribute User currentUser) {

        Article article = articleService.findById(id);
        if (article.getAuthor().equals(currentUser.getUserName())) {
            articleDelete(Config.get("picPath"), article);
            articleService.deleteById(id);
            logger.info("删除文章：" + article.getTitle() + "--id:" + article.getId());
        }
        return "redirect:/index.html";
    }

    private void articleDelete(String path,Article article) {

        ArrayList<String> picList = (ArrayList<String>) picService.findPicByArticleId(article.getId());
        if (picList != null) {
            for (String pic : picList) {
                File delFile = new File(path, pic);
//                if (delFile.delete()) {
                logger.info(delFile.getAbsolutePath() + "已成功删除");
//                } else {
//                    log.warn("无法删除图片：" + delFile.getAbsolutePath());
//                }
                logger.info("文章标题:" + article.getTitle());
                logger.info("文章id:" + article.getId());
            }
        }
        if (article.getCover() != null && !article.getCover().equals("")) {
            File cover = new File(path, article.getCover());
//            if (cover.delete()){
            logger.info("删除封面图片：" + cover.getAbsolutePath());

//            }else {
//                log.warn("删除封面图片失败：" + cover.getAbsolutePath());
//            }
            logger.info("文章标题" + article.getTitle());
            logger.info("文章id:" + article.getId());
        }
    }


    @RequestMapping({"/edit/{id}", "/edit"})
    public String editControl(HttpSession session, HttpServletRequest request, @PathVariable(value = "id", required = false) String id_str) {
        List<String> typeList = articleService.getTypeList();
        request.setAttribute("typeList", typeList);


        if (id_str != null && !id_str.equals("")) {
            User user = (User) session.getAttribute("currentUser");

            Article article = articleService.findById(Integer.valueOf(id_str));
            if (user.getUserName().equals(article.getAuthor())) {
                request.setAttribute("article", article);
            }
        }
        //先清空session中的uploadPic属性
        if (session.getAttribute("uploadPic") != null)
            session.removeAttribute("uploadPic");

        return "postedit";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String addArticle(@ModelAttribute("article") Article article, @RequestParam(value = "id", required = false) String id_str, @RequestParam("coverImg") CommonsMultipartFile coverImg, HttpSession session, HttpServletResponse response) {
        User currentUser = (User) session.getAttribute("currentUser");
        int id = 0;

        //判断用户是更新文章还是添加文章
        boolean isUpdate = false;

        if (id_str != null && !id_str.equals(""))
            isUpdate = true;
        if (isUpdate) {
            id = Integer.valueOf(id_str);
            Article old = articleService.findById(id);

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
                filename = ImgUploadUtil.upload(coverImg, Config.get("picPath"), 5000);
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
            articleService.createArticle(article);
            List<Article> articleList = articleService.findByTitle(article.getTitle());
            if (articleList.size() > 1)
                article = articleList.get(articleList.size() - 1);
            else
                article = articleList.get(0);
        } else {
            articleService.updateById(id, article);
        }

        ArrayList picList = (ArrayList) session.getAttribute("uploadPic");
        if (picList != null) {
            for (Object pic : picList) {
                picService.insertPic(pic.toString(), article.getId());
                logger.info(currentUser.getUserName() + ":添加图片信息--" + pic.toString() + " 文章标题：" + article.getTitle());
            }
        }


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
            fileName = ImgUploadUtil.upload(upload, Config.get("picPath"), 5000);
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
}
