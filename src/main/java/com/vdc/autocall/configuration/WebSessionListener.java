package com.vdc.autocall.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;
import java.util.Hashtable;


@Component
public class WebSessionListener implements HttpSessionListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static WebSessionListener sessionListener = null;
    private static Hashtable loginSessionList = new Hashtable();

    /**
     * 싱글톤 생성
     * @return
     */
    public static synchronized WebSessionListener getInstance() {
        if(sessionListener == null) {
            sessionListener = new WebSessionListener();
        }
        return sessionListener;
    }

    /**
     * session.setAttribute 실행 되는 순간 같은 sessionId 일경우 1회만 실행
     * @param httpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//        log.info("sessionCreated -> {}", httpSessionEvent.getSession().getAttribute("Emp_No"));
    }

    /**
     * session 이 소멸되는 시점에 실행, 기본 단위는 초(1분 미만은 설정할 수 없음)
     * @param httpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        log.info("sessionDestroyed 실행");
        HttpSession session = httpSessionEvent.getSession();

        String userId = (String) session.getAttribute("user_id");

        //로그아웃 유저 삭제

        synchronized(loginSessionList){
            loginSessionList.remove(httpSessionEvent.getSession().getId());
        }

        if(userId != null){
            this.updateUserCloseTime(userId);
        }

        currentSessionList();

    }

    /**
     * 현제 HashTable에 담겨 있는 유저 리스트, 즉 session list
     */
    private void currentSessionList(){
        Enumeration elements = loginSessionList.elements();
        HttpSession session = null;
        while (elements.hasMoreElements()){
            session = (HttpSession)elements.nextElement();

            String userId = (String)session.getAttribute("user_id");
            log.info("currentSessionUserList -> user_id : {} ", userId);
//            log.info("currentSessionUserList -> sessionId : {} ", session.getId());
//            log.info("currentSessionUserList -> hashtable SessionList {} : ", loginSessionList.get(session.getId()));
        }
    }

    /**
     * session 생성
     * @param request
     * @param value
     */
    public void setSession(HttpServletRequest request, String value){
//        log.info("setSession 실행");
        HttpSession session = request.getSession();
        session.setAttribute("user_id", value);

        //HashMap에 Login에 성공한 유저 담기
        synchronized(loginSessionList){
            loginSessionList.put(session.getId(), session);
        }

        currentSessionList();
    }

    /**
     * session 삭제
     * 세션이 remove 되면 destroyed 함수 실행
     * @param request
     */
    public void removeSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("user_id");
        String sessionId = session.getId();


        session.removeAttribute("user_id");
        session.invalidate();

//        log.info("removeSession 실행 : "+userId);

//        if(userId != null){
//            this.updateUserCloseTime(userId);
//        }

        currentSessionList();
    }

    /**
     * 유저 나간 시간 업데이트
     * @param userId
     */
    private void updateUserCloseTime(String userId) {
        log.info("updateUserCloseTime {} ", userId);
        //호출부에서 NULL 검사
        //업데이트 로직
    }

    /**
     * 현재 로그인한 유저가 이미 존재 하는지 확인
     * @param request
     * @param loginUserId
     * @return boolean
     */
    public boolean isLoginUser(HttpServletRequest request, String loginUserId, boolean remove){
        currentSessionList();

        Enumeration elements = loginSessionList.elements();
        HttpSession session = null;
        while (elements.hasMoreElements()){
            session = (HttpSession)elements.nextElement();
            String userId = (String)session.getAttribute("user_id");

            if(loginUserId.equals(userId) && (!session.getId().equals(request.getSession().getId()))){
                if (remove) session.invalidate(); //기존세션 제거
                return true;
            }

        }
        return false;
    }

    public boolean isLoginUser(HttpServletRequest request, String loginUserId){
        Enumeration elements = loginSessionList.elements();
        HttpSession session = null;
        while (elements.hasMoreElements()){
            session = (HttpSession)elements.nextElement();
            String userId = (String)session.getAttribute("user_id");

            if(loginUserId.equals(userId) && (!session.getId().equals(request.getSession().getId()))){
                return true;
            }

        }
        return false;
    }
}
