package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IArticleService;
import cn.xkenmon.blog.vo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class IndexController {

    final private IArticleService articleService;

    public IndexController(IArticleService articleService) {
        this.articleService = articleService;
    }


    @RequestMapping({"/", "/index.html"})
    public String doGet(HttpServletRequest req) {

        // TODO:用json传递数据实现前后端分离


        //获取最流行的文章
        List<Article> popularList = articleService.orderByReadCount(0, 3);

        //获取该类型文章数
        int total = articleService.queryCount();
        //获取当前页
        String page_str = req.getParameter("page");
        int page;
        if (page_str != null)
            page = Integer.valueOf(page_str);
        else page = 1;

        //获取最新首页文章
//        Article indexArticle = null;
//        if (page == 1) {
//            List<Article> indexArticles = articleService.findSimpleByType("index");
//            if (indexArticles.size() == 0) {
//                indexArticle = null;
//            } else
//                indexArticle = indexArticles.get(indexArticles.size() - 1);
//        }
        //判断排序方式（默认按创建时间排序）
        List<Article> articleList = null;
        String sortMethod;
        if (req.getParameter("sort") != null) {
            sortMethod = req.getParameter("sort");
            if (sortMethod.equals("" +
                    "click")) {
                articleList = articleService.orderByReadCount((page - 1) * 9, 9);
            } else if ((sortMethod.equals("createTime"))) {
                articleList = articleService.orderByCreateDate((page - 1) * 9, 9);
            }
        } else {
            articleList = articleService.orderByReadCount(9 * (page - 1), 9);
        }

        //获取文章类型列表
        List<String> typeList = articleService.getTypeList();

//        JSONArray articleArray = JSONArray.fromObject(articleList);
//        response.setCharacterEncoding("UTF-8");
//        try {
//            response.getWriter().print(articleArray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //设置req属性
        req.setAttribute("articleList", articleList);
        req.setAttribute("typeList", typeList);
        req.setAttribute("popularList", popularList);
        req.setAttribute("page", page);
        req.setAttribute("total", total);

        //转发页面
        return "main";
    }
}
