package com.sxj.controller;

import com.sxj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class RegisterControl {
    @Autowired
    UserService userService;

    // 实现注册功能
//    @RequestMapping(value = "/register",method = RequestMethod.POST)
//	public String signUp(@RequestParam("uname") String username,String password,HttpServletRequest request){
//    	 String un=request.getParameter("username");
//         String pd=request.getParameter("password");
//         System.out.println("un is:"+un);
//         System.out.println("pd is:"+password);
//         System.out.println("username is:"+username);
//         System.out.println("password is:"+password);
////        userService.Insert(username, password);
//        return "login.html";
//    }
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
    @PostMapping("/register")
    public void signUp(@RequestParam("loginType") String loginType, HttpServletRequest request, HttpServletResponse response, @RequestParam("uname") String username,
                       @RequestParam("upwd") String password) throws IOException {
//		请求编码
        request.setCharacterEncoding("UTF-8");
//    	@ModelAttribute("user") UserModel user
//		System.out.println(user);
//		System.out.println(user.getUname());
//		System.out.println(user.getUpwd());
//		System.out.println(user.getUid());

//		HttpServletRequest request
//		String un = request.getParameter("username");
//		String pd = request.getParameter("password");
//		System.out.println("un is:" + un);
//		System.out.println("pd is:" + password);

//		@RequestParam("uname") String username, @RequestParam("upwd") String password		
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);

        Integer insert = userService.Insert(username, password);
        System.out.println(insert);
        // 插入成功
//		if (insert > 0) {
//			return "redirect:login.html";
//		} else {
//			return "redirect:login.html";
//		}
        PrintWriter pw = response.getWriter();
        if (insert > 0) {
            pw.append("<h1>注册成功!</h1>5秒后跳转！");
        } else {
            pw.append("<h1>注册失败,检查用户名是否重复!</h1>5秒后跳转！");

        }

        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        // 返回Text
//		response.setContentType("text/plain; charset=UTF-8");
        // 返回Json
//		response.setContentType("application/json; charset=UTF-8");

        // 等同与调用response.setHeader(“content-type”, “text/html;charset=utf-8”)；
        response.setCharacterEncoding("utf-8");// 设置字符响应流的字符编码为utf-8；
//		response.setStatus(200);// 设置状态码；
        response.setStatus(250);
//		response.sendError(404, “您要查找的资源不存在”);//当发送错误状态码时，Tomcat会跳转到固定的错误页面去，但可以显示错误信息。
        // 五秒后自动跳转
        String loginTypeString = null;
        if (loginType.equals("mainMenu")) {
            loginTypeString = "mainMenu";
        } else if (loginType.equals("chessGame")) {
            loginTypeString = "chessGame";
        } else {

        }
        response.setHeader("Refresh", "3; login?loginType=" + loginTypeString);
//		跳404
        // response.setHeader("Refresh", "5; templates/error/404.html");
//		return "redirect:templates/error/404.html";
//		return "login.html";

    }
}
