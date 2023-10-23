package com.vdc.autocall.controller;


import com.vdc.autocall.common.resolver.CommandMap;
import com.vdc.autocall.service.DeviceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DeviceController {

    @Resource(name = "deviceService")
    private DeviceService deviceService;

    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;

    @RequestMapping("/login")
    public ModelAndView page_index() {
        return new ModelAndView("view/login");
    }

    @RequestMapping("/main")
    public ModelAndView main_index() {
        return new ModelAndView("view/main");
    }

    @RequestMapping("/login/proc")
    @ResponseBody
    public Map<String, Object> loginproc(@RequestParam("emp_no") String empNo, @RequestParam("pwd") String pwd, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (empNo.equals(username) && pwd.equals(password)) {
            session.setAttribute("emp_no", empNo);
            session.setAttribute("emp_nm", "오토콜");
            response.put("rst", true);
        } else {
            response.put("rst", false);
        }

        return response;
    }
@RequestMapping(value = "/logout")
    public ModelAndView Logout(HttpSession session) {
        if (session.getAttribute("emp_no") != null) {
            session.invalidate(); //세션 삭제
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value="/list")
    public ModelAndView DeviceList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        List<Map<String,Object>> dvc = deviceService.DeviceList(commandMap.getMap());
        mv.addObject("data", dvc);
        return mv;
    }

    @RequestMapping(value="/add")
    public ModelAndView DeviceAdd(CommandMap commandMap, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("jsonView");
        deviceService.addDevice(commandMap.getMap());
        return mv;
    }

    @RequestMapping(value="/edit")
    public ModelAndView DeviceEdit(CommandMap commandMap, HttpSession session) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        deviceService.editDevice(commandMap.getMap());
        return mv;
    }

    @RequestMapping(value="/del")
    public ModelAndView DeviceDel(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        deviceService.delDevice(commandMap.getMap());
        return mv;
    }
}

