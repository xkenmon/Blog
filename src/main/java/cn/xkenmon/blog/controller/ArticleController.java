package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IArticleService;
import cn.xkenmon.blog.vo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Article")
public class ArticleController {

    final private
    IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "{id_str}")
    public String doGet(HttpServletRequest req, @PathVariable String id_str) {

        int id = Integer.valueOf(id_str);
        Article article = articleService.findById(id);
        //如果没有对应的文章，转发页面到404
        if (article == null)
            return null;

        //获取文章类型得到推荐的最多三篇文章
        String type = article.getType();
        List<Article> relatedList = articleService.findByTypeOrderByClick(type, 0, 3);

        //获取最流行的文章
        List<Article> popularList = articleService.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = articleService.getTypeList();

        //设置request属性
        req.setAttribute("relatedList", relatedList);
        req.setAttribute("article", article);
        req.setAttribute("popularList", popularList);
        req.setAttribute("typeList", typeList);


        //更新文章阅读计数
        articleService.updateReadCountById(id, article.getReadCount() + 1);

        //转发到single页面
        return "single";
    }

}
