package cn.xkenmon.blog.control.manage;

import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.DAO.impl.UserDAOProxy;
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
public class Login {
    private static Logger log = LoggerFactory.getLogger(Login.class);

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, HttpSession session, HttpServletResponse response) {
        UserDAO dao = new UserDAOProxy();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            Writer out = response.getWriter();

            if (dao.findUserByName(user.getUserName()) != null) {
                out.write("<script>");
                out.write("alert(\"用户名已被注册\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
                log.debug("----用户名存在----");
                return null;
            } else if (dao.findUserByEmail(user.geteMail()) != null) {
                out.write("<script>");
                out.write("alert(\"邮箱已被注册\");");
                out.write("window.history.back(-1);");
                out.write("</script>");
                out.close();
            } else {
                user.setCreateDate(new Date());

                dao.createUser(user);
                out.write("<script>");
                out.write("alert(\"注册成功\");");
                out.write("window.history.go(-2);");
                out.write("</script>");
                out.close();
                log.info("\n----注册成功----\n" + user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        session.setAttribute("currentUser", user);
        return null;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user, HttpSession session, HttpServletResponse response) {
        UserDAO dao = new UserDAOProxy();
        User dbUser = dao.findUserByEmail(user.geteMail());
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
        } finally {
            dao.close();
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
            UserDAO dao = new UserDAOProxy();

            log.info("name:" + name);
            log.info("email:" + email);

            String msg = null;
            if (name != null) {
                log.info("-----------:befor:" + name);
                name = URLDecoder.decode(name, "utf-8");
                log.info("----------------------:" + name);
                User user = dao.findUserByName(name);
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
                User user = dao.findUserByEmail(email);
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
            UserDAO dao;
            try (PrintWriter out = response.getWriter()) {
                session.removeAttribute("currentUser");
                dao = new UserDAOProxy();
                dao.changeLogStatById(current.getId(), 0);
                out.write("<script>");
                out.write("window.name = \"ref\";");
                out.write("window.history.go(-1);");
                out.write("</script>");
                dao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("注销登录: "+ current.getUserName());
        }
        return null;

    }
}
