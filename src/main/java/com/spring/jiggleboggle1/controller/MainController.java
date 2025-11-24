package com.spring.jiggleboggle1.controller;

import com.spring.jiggleboggle1.security.CookieUtil;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final JwtUtil jwtUtil;
    private final RecipeService recipeService;
    private final CookieUtil cookieUtil;



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
    public String myPage(Model model, HttpServletRequest request) {

        String userId="";
        String token = cookieUtil.getTokenFromCookies(request, "JWT_TOKEN");
        if(token != null) {
            userId = jwtUtil.getUserIdFromToken(token);
        }

        List<RecipeVO> recipeList = recipeService.getMyRecipes(userId);

        model.addAttribute("recipeList", recipeList);
        model.addAttribute("userId", userId);

        // 뷰 반환
        return "user/myPage";
    }

    @GetMapping("/rankPage")
    public String rankPage(Model model) {

        List<RecipeVO> recipeList = recipeService.getRecipeList();
        model.addAttribute("recipeList", recipeList);

        return "main/RankPage";
    }

    @GetMapping("/aiPage")
    public String aiPage(Model model) {

        return "recipe/RecipeAiRecommend";
    }



}




