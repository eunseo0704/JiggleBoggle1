package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.config.JwtUtil;
import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.service.RecipeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final JwtUtil jwtUtil;
    private final RecipeService recipeService;



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
    @GetMapping("/UserMainPage")
    public String UserMainPage(Model model) {
        // 뷰 반환
        List<RecipeVO> recipeList = recipeService.getRecipeList();


        model.addAttribute("recipeList", recipeList);

        return "main/UserMainPage";
    }

}




