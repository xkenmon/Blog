package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IUserService;
import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.Date;

@Controller
@RequestMapping(value = "/manage")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    final private IUserService userService;

    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public void register(@ModelAttribute("user") User user, HttpSession session, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            Writer out = response.getWriter();

            if (userService.findUserByName(user.getUserName()) != null) {
                out.write("<script>");
                out.write("alert(\"用户名已被注册\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
                log.debug("----用户名存在----");
                return;
            } else if (userService.findUserByEmail(user.geteMail()) != null) {
                out.write("<script>");
                out.write("alert(\"邮箱已被注册\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
            } else {
                user.setCreateDate(new Date());

                userService.createUser(user);
                out.write("<script>");
                out.write("alert(\"注册成功\");");
                out.write("window.history.go(-2);");
                out.write("</script>");
                out.close();
                log.info("\n----注册成功----\n" + user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("currentUser", user);
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user, HttpSession session, HttpServletResponse response) {
        User dbUser = userService.findUserByEmail(user.geteMail());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Writer out;
        try {
            out = response.getWriter();

            if (dbUser == null) {
                out.write("<script>");
                out.write("alert(\"用户不存在\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
                log.debug("----用户不存在----");

                return null;
            } else if (dbUser.getPassWd().equals(user.getPassWd())) {
                session.setAttribute("currentUser", dbUser);
                out.write("<script>");
                out.write("alert(\"欢迎回来^_^ " + dbUser.getUserName() + "\");");
                out.write("window.name = \"ref\";");
                out.write("window.history.go(-2);");
                out.write("</script>");
                out.close();
                log.info("\n登陆成功:\n" + dbUser.toString());
                return null;
            } else {
                out.write("<script>");
                out.write("alert(\"密码错误\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
                log.debug("\n密码错误:\n" + dbUser.toString());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/registerCheck", method = RequestMethod.POST)
    public String userCheck(HttpServletResponse response,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "email", required = false) String email) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();

            log.info("name:" + name);
            log.info("email:" + email);

            String msg = null;
            if (name != null) {
                log.info("-----------:befor:" + name);
                name = URLDecoder.decode(name, "utf-8");
                log.info("----------------------:" + name);
                User user = userService.findUserByName(name);
                if (user == null) {
                    msg = "该用户名可以注册";
                } else {
                    msg = "此用户名已被注册";
                    log.info(user.toString());
                }
            }

            if (email != null) {
                log.info("-----------:befor:" + email);
                email = URLDecoder.decode(email, "utf-8");
                log.info("----------------------:" + email);
                User user = userService.findUserByEmail(email);
                if (user == null) {
                    msg = "该邮箱可以注册";
                } else {
                    msg = "此邮箱已被注册";
                    log.info(user.toString());
                }
            }


            if (msg != null) {
                out.write(msg);
                log.info(msg);
            }
            out.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/logout.do")
    public String logout(HttpSession session, HttpServletResponse response) {
        User current = (User) session.getAttribute("currentUser");
        if (current != null) {
            try (PrintWriter out = response.getWriter()) {
                session.removeAttribute("currentUser");
                userService.changeLogStatById(current.getId(), 0);
                out.write("<script>");
                out.write("window.name = \"ref\";");
                out.write("window.history.go(-1);");
                out.write("</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("注销登录: "+ current.getUserName());
        }
        return null;

    }
}
