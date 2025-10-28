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
    public String mainPage(Model model, HttpServletRequest request,
                           @CookieValue(value = "JWT_TOKEN", required = false) String token) {


        // 2. 쿠키에서 JMT_TOKEN 가져오기
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 3. JWT 유효성 체크
        if (token != null && jwtUtil.validateToken(token)) {
            String userId = jwtUtil.getUserIdFromToken(token);
            String userName = jwtUtil.getUserNameFromToken(token);

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", userName);
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        // 4. 뷰 반환
        return "main/MainPage";
    }

}




