package com.sxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ChessControl {
    //	all
    @RequestMapping("/chess")
//	post
//	@RequestMapping(value = "/chess", method = RequestMethod.POST)
//	@PostMapping("/chess")
//	get
//	@GetMapping("/chess")

    public String mainMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        // 返回html
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("utf-8");// 设置字符响应流的字符编码为utf-8；
        response.setStatus(250);
        String welcomToYou = (String) session.getAttribute("welcomToYou");
        String uname = (String) session.getAttribute("uname");
        PrintWriter pw = response.getWriter();
        if (uname == null || welcomToYou == null) {
//            pw.write("<h1>请先登陆！</h1><span>五秒后自动跳转</span>");
//
//            // 五秒后自动跳转
//            response.setHeader("Refresh", "5; login.html");
            return "redirect:login?loginType=chessGame";

        } else {
//			append
//			write
//			print
            /*
             * pw.write("<div id='userDiv'><div><span type=\"text\">" + uname +
             * "</span></div>");
             */
        }
        return "chessGame.html";
//        return "forward:chessGame.html?loginId="+uname;
    }

}
