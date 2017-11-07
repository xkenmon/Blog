package cn.xkenmon.blog.control.listener;

import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.DAO.impl.UserDAOProxy;
import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;

public class UserLogInfListener implements HttpSessionAttributeListener, HttpSessionListener {

    private static Logger log = LoggerFactory.getLogger(UserLogInfListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionEvent) {
        UserDAO dao = new UserDAOProxy();
        User currentUser = (User) httpSessionEvent.getSession().getAttribute("currentUser");
        dao.changeLogStatById(currentUser.getId(), 1);
        log.debug(currentUser.toString());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionEvent) {
        UserDAO dao = new UserDAOProxy();
        User currentUser;
        if (httpSessionEvent.getName().equals("currentUser")) {
            currentUser = (User) httpSessionEvent.getValue();
            dao.changeLogStatById(currentUser.getId(), 0);
            log.debug(currentUser.getUserName() + ":changeLogStat = 0");
        }

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionEvent) {
        UserDAO dao = new UserDAOProxy();
        User currentUser = (User) httpSessionEvent.getSession().getAttribute("currentUser");
        dao.changeLogStatById(currentUser.getId(), 1);
        log.debug(currentUser.getUserName() + ":changeLogStat = 1");
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            log.debug("\n---------this create session don't have user-----");
            return;
        }
        UserDAO dao = new UserDAOProxy();
        dao.changeLogStatById(currentUser.getId(), 1);
        log.debug("\n-----change login status:" + currentUser.getId() + " : " + currentUser.getUserName() + " = 1---------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            log.debug("\n--------this destroy session don't  have user-----------");
            return;
        }
        UserDAO dao = new UserDAOProxy();
        dao.changeLogStatById(currentUser.getId(), 0);
        log.debug("\n-----------change login status:" + currentUser.getId() + " : " + currentUser.getId() + " = 0------------");
    }
}
