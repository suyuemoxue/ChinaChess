package com.sxj.controller;

import com.sxj.entity.UserBean;
import com.sxj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class LoginControl {
    @Resource
    UserService userService;

    // 实现登录
    @RequestMapping("/login")
    public String show(@RequestParam("loginType") String loginType, Model model, HttpServletRequest request,
                       HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println(loginType);

        String sessionUserName = (String) session.getAttribute("uname");
        System.out.println(sessionUserName);
        request.setCharacterEncoding("UTF-8");

        PrintWriter pw = response.getWriter();

        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        // 设置字符响应流的字符编码为utf-8；
        response.setCharacterEncoding("utf-8");
        /* 查询是否处于登陆状态 */
        if (sessionUserName != null) {
            /* pw.append("<h1>已经处于登陆状态!</h1>3秒后跳转！"); */
            /* 打印 */
            /* pw.print("<h1>已经处于登陆状态!</h1>3秒后跳转"); */
            /* 写入 */
            pw.write("<h1>已经处于登陆状态!</h1>3秒后跳转");

            if (loginType.equals("mainMenu")) {
                response.setHeader("Refresh", "3; mainMenuController");
            } else if (loginType.equals("chessGame")) {
                response.setHeader("Refresh", "3; chess");
            }

            return "error/403.html";
        } else {
            model.addAttribute("loginType", loginType);
            return "login.html";
        }

    }

    @RequestMapping(value = "/loginIn", method = RequestMethod.POST)
    public void login(@RequestParam("uname") String uname, @RequestParam("upwd") String upwd,
                      HttpServletRequest request, HttpServletResponse response, HttpSession session,
                      @RequestParam("loginType") String loginType) throws IOException, ServletException, InterruptedException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("--------------登陆类型--------------");
        UserBean userBean = userService.LoginIn(uname, upwd);
        PrintWriter pw = response.getWriter();
        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        // 设置字符响应流的字符编码为utf-8；
        response.setCharacterEncoding("utf-8");
        // 状态码
        response.setStatus(250);
        if (userBean != null) {
            pw.append("<h1>登陆成功!</h1>");
//			session储存
            session.setAttribute("welcomToYou", "欢迎你！");
            session.setAttribute("uname", uname);
            //设置session超时(秒)
            session.setMaxInactiveInterval(1000);

//			response.sendRedirect("mainMenuController");
            if (loginType.equals("mainMenu")) {
                response.setHeader("Refresh", "3; mainMenuController");
            } else if (loginType.equals("chessGame")) {
                response.setHeader("Refresh", "3; chess");
            }
            /* return "mainMenu.html"; */
        } else {
            pw.append("<h1>检查用户名是否正确!</h1>5秒后跳转！");
            // 五秒后自动跳转
            response.setHeader("Refresh", "5; login?loginType=" + loginType);

            /*
             * Thread.sleep(5000); return "redirect:/login.html";
             */
        }
        System.out.println("--------------登陆类型--------------");
    }

    @RequestMapping("/index.html")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/signup")
    public String disp() {
        return "signup";
    }

    //	注销
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        PrintWriter pw = response.getWriter();
        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        pw.append("<h1>注销成功!</h1>5秒后跳转！");

        session.removeAttribute("welcomToYou");
        session.removeAttribute("uname");
        // 五秒后自动跳转
        /* response.setHeader("Refresh", "5; login.html"); */
        response.setHeader("Refresh", "5; mainMenuController");
    }
}
