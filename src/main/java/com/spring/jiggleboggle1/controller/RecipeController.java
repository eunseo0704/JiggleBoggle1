package com.spring.jiggleboggle1.controller;


import com.spring.jiggleboggle1.domain.CodeVO;
import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.service.CodeService;
import com.spring.jiggleboggle1.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final RecipeService recipeService;
    private final CodeService codeService;

    @GetMapping("/recipeList")
    public String recipeList(Model model,RecipeVO recipeVo) {

        List<RecipeVO> recipeList = recipeService.getRecipeList();
        model.addAttribute("recipeList", recipeList);
        model.addAttribute("recipeVo", recipeVo);

        return "recipe/RecipeList";
    }

    @GetMapping("/searchRecipe")
    public String searchRecipe(@RequestParam String searchName, Model model) {

        List<RecipeVO> recipeList = recipeService.getSearchList(searchName);


        model.addAttribute("recipeList", recipeList);
        model.addAttribute("searchName", searchName);
 //       redirectAttributes.addFlashAttribute("recipeList", recipeList);
 //       redirectAttributes.addFlashAttribute("searchName", searchName);


        return "recipe/RecipeList";

    }

    @GetMapping("/recipeWrite")
    public String recipeWrite(Model model) {

        List<CodeVO> categoryList = codeService.codeList("CTG");

        model.addAttribute("categoryList", categoryList);

        return "recipe/RecipeWrite";

    }

    @PostMapping("saveRecipeData")
    public String recipeWrite(Model model, RecipeVO recipeVo, RedirectAttributes redirectAttributes) {

        int result = 0;

        result = recipeService.saveRecipeData(recipeVo);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
            return "redirect:/recipeWrite";
        } else {
            redirectAttributes.addFlashAttribute("msg", "회원가입이 실패하였습니다.");
            return "redirect:/signUpUserData";
        }
    }

}
