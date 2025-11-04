package com.spring.jiggleboggle1.service;

import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.mapper.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeMapper recipeMapper;


    public List<RecipeVO> getRecipeList() {
        return recipeMapper.getRecipeList();
    }

    public List<RecipeVO> getSearchList(String searchName)  {
        return recipeMapper.getSearchList(searchName);
    }
}
