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
        HttpSession session = request.getSession();

        //Session 확인
        if (!hasSessionInfo(session)) {

            //Ajax 호출의경우 SendError 코드 전송
            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
                response.sendError(901);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        }

        return true;
    }

    //Controller 접근후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {

        if(modelAndView == null) return;

        //페이지 호출의경우 세션정보를 Model에 넣는다.
        if (!"XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
            HttpSession session = request.getSession();

            Map<String, String> map = new HashMap<>();
            map.put("emp_no", session.getAttribute("emp_no").toString());
            map.put("emp_nm", session.getAttribute("emp_nm").toString());
            modelAndView.addObject("User", map);

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) {
    }

    private boolean hasSessionInfo(HttpSession session)
    {
        return (session.getAttribute("emp_no") != null);
    }


}
