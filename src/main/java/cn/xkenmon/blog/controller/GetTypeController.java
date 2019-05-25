package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IArticleService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/")
class GetTypeController {

    final private IArticleService articleService;

    GetTypeController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/getType")
    public void get(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        response.setContentType("json");
        List<String> list = articleService.getTypeList();
        JSONArray array = JSONArray.fromObject(list);
        out.print(array);
        out.close();
    }
}
