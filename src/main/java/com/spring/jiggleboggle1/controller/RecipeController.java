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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recipeList")
    public String recipeList(Model model) {

        List<RecipeVO> recipeList = recipeService.getRecipeList();
        model.addAttribute("recipeList", recipeList);

        return "recipe/RecipeList";
    }

    @GetMapping("/searchRecipe")
    public String searchRecipe(Model model, @RequestParam String searchName) {

        List<RecipeVO> recipeList = recipeService.getSearchList(searchName);

        model.addAttribute("recipeList", recipeList);
        return "recipe/RecipeList";

    }

}
