package cn.xkenmon.blog.listener;

import cn.xkenmon.blog.service.IUserService;
import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.*;
import java.util.Objects;

public class UserLogInfListener implements HttpSessionAttributeListener, HttpSessionListener {

    private static Logger log = LoggerFactory.getLogger(UserLogInfListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionEvent) {
        IUserService userService = Objects.requireNonNull(WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext())).getBean(IUserService.class);
        User currentUser = (User) httpSessionEvent.getSession().getAttribute("currentUser");
        userService.changeLogStatById(currentUser.getId(), 1);
        log.debug(currentUser.toString());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionEvent) {
        IUserService userService = Objects.requireNonNull(WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext())).getBean(IUserService.class);
        User currentUser;
        if (httpSessionEvent.getName().equals("currentUser")) {
            currentUser = (User) httpSessionEvent.getValue();
            userService.changeLogStatById(currentUser.getId(), 0);
            log.debug(currentUser.getUserName() + ":changeLogStat = 0");
        }

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionEvent) {
        IUserService userService = Objects.requireNonNull(WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext())).getBean(IUserService.class);
        User currentUser = (User) httpSessionEvent.getSession().getAttribute("currentUser");
        userService.changeLogStatById(currentUser.getId(), 1);
        log.debug(currentUser.getUserName() + ":changeLogStat = 1");
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        IUserService userService = Objects.requireNonNull(WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext())).getBean(IUserService.class);
        HttpSession session = httpSessionEvent.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            log.debug("\n---------this create session don't have user-----");
            return;
        }
        userService.changeLogStatById(currentUser.getId(), 1);
        log.debug("\n-----change login status:" + currentUser.getId() + " : " + currentUser.getUserName() + " = 1---------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        IUserService userService = Objects.requireNonNull(WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext())).getBean(IUserService.class);
        HttpSession session = httpSessionEvent.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            log.debug("\n--------this destroy session don't  have user-----------");
            return;
        }
        userService.changeLogStatById(currentUser.getId(), 0);
        log.debug("\n-----------change login status:" + currentUser.getId() + " : " + currentUser.getId() + " = 0------------");
    }
}
