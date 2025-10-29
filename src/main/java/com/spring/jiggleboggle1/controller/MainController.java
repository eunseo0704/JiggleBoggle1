package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final JwtUtil jwtUtil;

    public MainController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/signup")
    public String signup() {

        // return "comm/Signup";
        return "comm/Signup";
    }

    @GetMapping("/login")
    public String login() {

        // return "comm/Signup";
        return "comm/login";
    }

    @GetMapping("/MainPage")
    public String mainPage(Model model) {
        // 뷰 반환
        return "main/MainPage";
    }

}




