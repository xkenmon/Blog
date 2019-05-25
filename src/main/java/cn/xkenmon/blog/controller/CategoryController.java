package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IArticleService;
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
public class CategoryController {

    final private
    IArticleService articleService;

    public CategoryController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/{type}")
    public String doGet(HttpServletRequest req, @PathVariable String type){

        //获取排序方式
        String sort = req.getParameter("sort");
        if (sort == null || sort.equals(""))
            sort = "click";

        //获取该类型文章数
        int total = articleService.queryCountByColumn("type", type);
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
                articleList = articleService.findByTypeOrderByDate(type, page * 9, 9);
            else
                articleList = articleService.findByTypeOrderByClick(type, page * 9, 9);
        } else {
            articleList = articleService.orderByReadCount(page * 9, 9);
        }

        //
        if (sort.equals("click")) {
            articleList = articleService.findSimpleByTypeOrderByClick(type, (page - 1) * 9, 9);
        } else if ((sort.equals("createTime"))) {
            articleList = articleService.findSimpleByTypeOrderByCreateTime(type, (page - 1) * 9, 9);
        }


        //获取最流行的文章
        List<Article> popularList = articleService.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = articleService.getTypeList();

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
