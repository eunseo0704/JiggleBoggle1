package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.service.UserService;
import com.spring.jiggleboggle1.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUpUserData")
    public String signUpUserData(@ModelAttribute UserVO userVO, Model model) {

        int result = 0;

        result = userService.setSignUp(userVO);

        model.addAttribute("userVO", userVO);

        return "join/signUpPage";
    }
}