package com.vdc.autocall.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    //Controller 접근전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean rst = true;
        HttpSession session = request.getSession();
        if (!hasSessionInfo(session)) {
//            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
//            response.sendError(401, "no session or session expire"); //세선 만료시 401 리턴
//            }
            response.sendRedirect(request.getContextPath() + "/login"); // 세션 만료시 /login으로 리다이렉트
            rst = false;
        }
        return rst;
    }

    //Controller 접근후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(modelAndView == null) return;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) {
    }

    private boolean hasSessionInfo(HttpSession session)
    {
        return (session.getAttribute("user_id") != null);
    }


}
