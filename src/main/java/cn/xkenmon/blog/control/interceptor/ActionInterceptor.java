package cn.xkenmon.blog.control.interceptor;

import cn.xkenmon.blog.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(ActionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser != null) {
            logger.info(currentUser.getUserName() + "---" + request.getRequestURL() + "   query: " + request.getQueryString());
        } else
            logger.info("匿名用户---" + request.getRequestURL() + "   query: " + request.getQueryString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
