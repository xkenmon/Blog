package cn.xkenmon.blog.control.servlet;


import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.DAOFactory;
import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.DAO.impl.UserDAOProxy;
import cn.xkenmon.blog.vo.Article;
import cn.xkenmon.blog.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ServletProfileControl {
    @RequestMapping(value = "/{username}")
    public String doGet(HttpServletRequest req, @PathVariable String username) {

        ArticlesDAO articlesDAO = DAOFactory.getInstance();
        UserDAO userDAO = new UserDAOProxy();

        User user = userDAO.findUserByName(username);

        //获取排序方式
        String sort = req.getParameter("sort");
        if (sort == null || sort.equals(""))
            sort = "click";

        //获取该类型文章数
        int total = articlesDAO.queryCountByColumn("author", user.getUserName());
        //获取当前页
        String page_str = req.getParameter("page");
        int page = 1;
        if (page_str != null)
            page = Integer.valueOf(page_str);

        //获取文章列表
        List<Article> articleList;

        if (sort.equals("click")) {
            articleList = articlesDAO.findByAuthorOrderByClick(user.getUserName(), (page - 1) * 9, 9);
        } else{
            articleList = articlesDAO.findByAuthorOrderByDate(user.getUserName(), (page - 1) * 9, 9);
        }

        //获取最流行的文章
        List<Article> popularList = articlesDAO.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = articlesDAO.getTypeList();

        articlesDAO.close();

        req.setAttribute("articleList", articleList);
        req.setAttribute("typeList", typeList);
        req.setAttribute("popularList", popularList);
        req.setAttribute("page", page);
        req.setAttribute("total", total);
        req.setAttribute("sort", sort);
        req.setAttribute("user", user);

        return "/profile";
    }
}
