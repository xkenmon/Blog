package cn.xkenmon.blog.controller;


import cn.xkenmon.blog.service.IArticleService;
import cn.xkenmon.blog.service.IUserService;
import cn.xkenmon.blog.vo.Article;
import cn.xkenmon.blog.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    final private IUserService userService;

    final private IArticleService articleService;

    public ProfileController(IUserService userService, IArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @RequestMapping(value = "/{username}")
    public String doGet(HttpServletRequest req, @PathVariable String username) {

        User user = userService.findUserByName(username);

        //获取排序方式
        String sort = req.getParameter("sort");
        if (sort == null || sort.equals(""))
            sort = "click";

        //获取该类型文章数
        int total = articleService.queryCountByColumn("author", user.getUserName());
        //获取当前页
        String page_str = req.getParameter("page");
        int page = 1;
        if (page_str != null)
            page = Integer.valueOf(page_str);

        //获取文章列表
        List<Article> articleList;

        if (sort.equals("click")) {
            articleList = articleService.findByAuthorOrderByClick(user.getUserName(), (page - 1) * 9, 9);
        } else {
            articleList = articleService.findByAuthorOrderByDate(user.getUserName(), (page - 1) * 9, 9);
        }

        //获取最流行的文章
        List<Article> popularList = articleService.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = articleService.getTypeList();

        req.setAttribute("articleList", articleList);
        req.setAttribute("typeList", typeList);
        req.setAttribute("popularList", popularList);
        req.setAttribute("page", page);
        req.setAttribute("total", total);
        req.setAttribute("sort", sort);
        req.setAttribute("user", user);

        return "profile";
    }
}
