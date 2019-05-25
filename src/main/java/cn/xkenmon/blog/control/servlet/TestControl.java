package cn.xkenmon.blog.control.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestControl {
    private final
    ServletContext context;

    public TestControl(ServletContext context) {
        this.context = context;
    }

    @RequestMapping
    public void test(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        resp.getWriter().println(applicationContext);
    }
}
