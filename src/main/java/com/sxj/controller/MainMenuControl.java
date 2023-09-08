package com.sxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MainMenuControl {
    //	all
    @RequestMapping("/mainMenuController")
//	post
//	@RequestMapping(value = "/mainMenuController", method = RequestMethod.POST)
//	@PostMapping("/mainMenuController")
//	get
    // @GetMapping("/mainMenuController")

    public String mainMenuController(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("utf-8");// 设置字符响应流的字符编码为utf-8；
        response.setStatus(250);
        String welcomToYou = (String) session.getAttribute("welcomToYou");
        String uname = (String) session.getAttribute("uname");
        System.out.println(uname);
        PrintWriter pw = response.getWriter();
        if (uname == null || welcomToYou == null) {
            /* pw.append("<div id='userDiv'><h1><a href='login.html'>登录</a></h1></div>"); */
            pw.append("<div id='userDiv'><button type='button' class='btn btn-default btn-lg'><h1> <span class='glyphicon glyphicon-user'><a href='login?loginType=mainMenu'>登录</a></h1></button></div>");

            // 五秒后自动跳转
            /* response.setHeader("Refresh", "5; login.html"); */
//			return "mainMenu.html";

        } else {

            pw.append("<div id='userDiv'><button type='button' class='btn btn-default btn-lg'><h1> <span class='glyphicon glyphicon-user'><span type=\"text\">" + uname
                    + "</span><a href=\"loginOut\">注销</a></button></div>");
        }
        return "mainMenu.html";
    }

    //	all
    @RequestMapping("/backgroundController")
    public String backgroundController() {
        /*return "../static/video/background.mp4";*/
        return "redirect:video/background.mp4";
    }
}
