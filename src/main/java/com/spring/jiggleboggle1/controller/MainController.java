package com.spring.jiggleboggle1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/MainPage")
    public String MainPage() {

        return "main/MainPage";
    }

    @GetMapping("/signup")
    public String signup() {

        return "comm/Signup";
    }




}
