package cn.xkenmon.blog.control.servlet;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.DAOFactory;
import cn.xkenmon.blog.vo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Article")
public class ServletSingleControl{
    @RequestMapping(value = "{id_str}")
    public String doGet(HttpServletRequest req, @PathVariable String id_str){

        int id = Integer.valueOf(id_str);
        ArticlesDAO dao = DAOFactory.getInstance();
        Article article = dao.findById(id);
        //如果没有对应的文章，转发页面到404
        if (article == null)
            return null;

        //获取文章类型得到推荐的最多三篇文章
        String type = article.getType();
        List<Article> relatedList = dao.findByTypeOrderByClick(type, 0,3);

        //获取最流行的文章
        List<Article> popularList = dao.orderByReadCount(0, 3);

        //获取文章类型列表
        List<String> typeList = dao.getTypeList();

        //设置request属性
        req.setAttribute("relatedList", relatedList);
        req.setAttribute("article", article);
        req.setAttribute("popularList", popularList);
        req.setAttribute("typeList", typeList);


        //更新文章阅读计数
        dao.updateReadCountById(id, article.getReadCount() + 1);

//        关闭数据库连接
        dao.close();

        //转发到single页面
        return "single";
    }

}
