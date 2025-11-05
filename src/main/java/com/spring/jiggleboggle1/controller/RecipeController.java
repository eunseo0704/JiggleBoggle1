package com.spring.jiggleboggle1.controller;


import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

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

}
