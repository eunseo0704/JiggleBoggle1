package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.security.JwtUtil;
import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.service.RecipeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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
    public String mainPage(HttpServletRequest request, Model model, OAuth2AuthenticationToken auth, RecipeVO recipeVo) {

        String jwt = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> c.getName().equals("JWT_TOKEN"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (jwt ==null || !jwtUtil.validateToken(jwt)) {
            return "main/MainPage";
        }else{

            List<RecipeVO> recipeList = recipeService.getRecipeList();
            model.addAttribute("recipeList", recipeList);
            model.addAttribute("recipeVo", recipeVo);

            return "main/HomePage";
        }
        // 뷰 반환
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {
        // 뷰 반환
        return "user/myPage";
    }


}




