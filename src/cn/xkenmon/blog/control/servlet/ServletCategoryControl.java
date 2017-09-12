package cn.xkenmon.blog.control.servlet;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.DAOFactory;
import cn.xkenmon.blog.vo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/category")
public class ServletCategoryControl {
    @RequestMapping(value = "/{type}")
    public String doGet(HttpServletRequest req, @PathVariable String type){

        ArticlesDAO dao = DAOFactory.getInstance();

        //获取排序方式
        String sort = req.getParameter("sort");
        if (sort == null || sort.equals(""))
            sort = "click";

        //获取该类型文章数
        int total = dao.queryCountByColumn("type", type);
        //获取当前页
        String page_str = req.getParameter("page");
        int page = 1;
        if (page_str != null)
            page = Integer.valueOf(page_str);

        //获取文章列表
        List<Article> articleList;
        if (type != null && !type.equals("")) {
            try {
                type = URLDecoder.decode(type, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sort.equals("createTime"))
                articleList = dao.findByTypeOrderByDate(type, page * 9, 9);
            else
                articleList = dao.findByTypeOrderByClick(type, page * 9, 9);
        } else {
            articleList = dao.orderByReadCount(page * 9, 9);
        }

        //
        if (sort.equals("click")) {
            articleList = dao.findSimpleByTypeOrderByClick(type, (page - 1) * 9, 9);
        } else if ((sort.equals("createTime"))) {
            articleList = dao.findSimpleByTypeOrderByCreateTime(type, (page - 1) * 9, 9);
        }


        //获取最流行的文章
        List<Article> popularList = dao.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = dao.getTypeList();

        dao.close();

        req.setAttribute("articleList", articleList);
        req.setAttribute("type", type);
        req.setAttribute("typeList", typeList);
        req.setAttribute("popularList", popularList);
        req.setAttribute("page", page);
        req.setAttribute("total", total);
        req.setAttribute("sort",sort);


        return "category";
    }
}
