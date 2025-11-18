package com.spring.jiggleboggle1.controller;


import com.spring.jiggleboggle1.domain.CodeVO;
import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.security.CookieUtil;
import com.spring.jiggleboggle1.security.JwtUtil;
import com.spring.jiggleboggle1.service.CodeService;
import com.spring.jiggleboggle1.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final CodeService codeService;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    @GetMapping("/recipeList")
    public String recipeList(Model model,RecipeVO recipeVo) {

        List<RecipeVO> recipeList = recipeService.getRecipeList();
        model.addAttribute("recipeList", recipeList);
       // model.addAttribute("recipeVo", recipeVo);

        return "recipe/RecipeList";
    }

    @GetMapping("/searchRecipe")
    public String searchRecipe(@RequestParam String searchName, Model model) {

        List<RecipeVO> recipeList = recipeService.getSearchList(searchName);

        model.addAttribute("recipeList", recipeList);

        return "recipe/RecipeList";

    }

    @GetMapping("/recipeWrite")
    public String recipeWrite(Model model) {

        List<CodeVO> categoryList = codeService.codeList("CTG");
        List<CodeVO> cookDfctList = codeService.codeList("DFC");
        List<CodeVO> cooktimeList = codeService.codeList("COT");

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("cookDfctList", cookDfctList);
        model.addAttribute("cooktimeList", cooktimeList);

        return "recipe/RecipeWrite";

    }

    @PostMapping("/saveRecipeData")
    public String recipeWrite(RecipeVO recipeVo, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        int result;

        String gubun;

        // JWT 쿠키에서 토큰 가져오기
        String token = cookieUtil.getTokenFromCookies(request, "JWT_TOKEN");
        String userId = jwtUtil.getUserIdFromToken(token);

        recipeVo.setUserId(userId);

        result = recipeService.saveRecipeFormData(recipeVo);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "저장성공"); //성공페이지 이동
            return "redirect:/MainPage"; // 성공페이지 생성
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장 실패하였습니다."); // 실패
            return "redirect:/recipeWrite";
        }
    }

    @GetMapping("/temp")
    public String tempPage(Model model, @RequestParam String recipeTitle) {
        model.addAttribute("recipeTitle", recipeTitle);
        return "recipe/temp";
    }

}
