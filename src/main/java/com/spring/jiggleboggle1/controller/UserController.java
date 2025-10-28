package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.service.UserService;
import com.spring.jiggleboggle1.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUpUserData")
    public String signUpUserData(@ModelAttribute UserVO userVO, Model model, RedirectAttributes redirectAttributes) {

        int result = 0;
        result = userService.setSignUp(userVO);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
            return "redirect:/MainPage";
        } else {
            redirectAttributes.addFlashAttribute("msg","회원가입이 실패하였습니다.");
            return "redirect:/signUpUserData";
        }



    }
}