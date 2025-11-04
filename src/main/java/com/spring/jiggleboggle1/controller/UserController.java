package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.config.JwtUtil;
import com.spring.jiggleboggle1.service.UserService;
import com.spring.jiggleboggle1.domain.UserVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signUpUserData")
    public String signUpUserData(@ModelAttribute UserVO userVO, Model model, RedirectAttributes redirectAttributes) {

        int result = 0;
        result = userService.setSignUp(userVO);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
            return "redirect:/MainPage";
        } else {
            redirectAttributes.addFlashAttribute("msg", "회원가입이 실패하였습니다.");
            return "redirect:/signUpUserData";
        }
    }

    @PostMapping("/userLogin")
    public String loginUser(@RequestParam String userId,
                            @RequestParam String userPswd,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {

        UserVO loginUser = userService.loginUser(userId, userPswd);


        if (loginUser != null) {
            // JMT 발급
            String token = jwtUtil.generateToken(loginUser.getUserId(), loginUser.getUserName());

            // JMT를 HttpOnly 쿠키로 브라우저에 저장
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600);
            response.addCookie(jwtCookie);

            return "redirect:/MainPage";

        } else {
            redirectAttributes.addFlashAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(jwtCookie);

        redirectAttributes.addFlashAttribute("msg", "로그아웃 되었습니다.");
        return "redirect:/login";
    }



}