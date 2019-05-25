package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Controller
@RequestMapping("/")
public class PingControlController {
    private static Logger logger = LoggerFactory.getLogger(PingControlController.class);

    @RequestMapping("/ping")
    public void ping(@RequestParam("sid") String sid, HttpServletResponse response, HttpServletRequest request) {

        try {
            Writer out = response.getWriter();
            User user = (User) request.getSession().getAttribute("currentUser");
            out.write(user == null ? "anonymous" : user.getUserName() + " ping");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
