package cn.xkenmon.blog.control.servlet;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.DAOFactory;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/")
class ServletGetType {
    @RequestMapping("/getType")
    public void get(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        response.setContentType("json");
        ArticlesDAO dao = DAOFactory.getInstance();
        List<String> list = dao.getTypeList();
        JSONArray array = JSONArray.fromObject(list);
        out.print(array);
        out.close();
    }
}
