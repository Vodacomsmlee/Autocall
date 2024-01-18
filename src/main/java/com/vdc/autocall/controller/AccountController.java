package com.vdc.autocall.controller;

import com.vdc.autocall.common.resolver.CommandMap;
import com.vdc.autocall.configuration.WebSessionListener;
import com.vdc.autocall.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Controller
public class AccountController {

    @Resource(name = "accountService")
    private AccountService accountService;
    @Resource(name = "webSessionListener")
    private WebSessionListener webSessionListener;

    @RequestMapping(value = "/account/add")
    @ResponseBody
    public Map<String, Object> account_add(CommandMap commandMap) {

        Map<String, Object> Map = new HashMap<>();

        try {
            accountService.account_add(commandMap.getMap());
            Map.put("success", true);
        } catch (Exception e) {
            Map.put("success", false);
            Map.put("msg", e.getMessage());
        }
        return Map;

    }

    @RequestMapping("/login")
    public ModelAndView page_index() {
        return new ModelAndView("view/login");
    }

    @RequestMapping("/main")
    public ModelAndView main_index() {
        return new ModelAndView("view/main");
    }


    @RequestMapping(value = "/login/proc")
    @ResponseBody
    public Map<String, Object> LoginCheck(CommandMap commandMap, HttpServletRequest request) throws Exception {
        Map<String, Object> Rst = new HashMap<>();
        Map<String, Object> LoginRst = accountService.account_login(commandMap.getMap());

        boolean success = false;
        String msg = "";


        if (LoginRst != null && !LoginRst.isEmpty()) {

            HttpSession session = request.getSession();
            if (!webSessionListener.isLoginUser(request, LoginRst.get("user_id").toString())) {
                session.setAttribute("user_id", URLDecoder.decode(LoginRst.get("user_id").toString(), "UTF-8"));
                webSessionListener.setSession(request, URLDecoder.decode(LoginRst.get("user_id").toString(), "UTF-8"));

                success = true;
                msg = "login ok";
                Rst.put("token", session.getId());
                Rst.put("rst", LoginRst);

            } else {
                msg = "Duplicate login user";
            }
        }
        Rst.put("success", success);
        Rst.put("msg", msg);
        return Rst;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String, Object> LogOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> Rst = new HashMap<>();
        try {
            if (session.getAttribute("user_id") != null) {
                webSessionListener.removeSession(request);
            }
            Rst.put("success", true);
            Rst.put("msg", "logout");
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login");

        } catch (Exception e) {
            Rst.put("success", false);
            Rst.put("msg", e.getMessage());
        }

        return Rst;
    }
}