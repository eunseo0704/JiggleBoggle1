package com.spring.jiggleboggle1.mapper;

import com.spring.jiggleboggle1.domain.RecipeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    public List<RecipeVO> getRecipeList();

    public List<RecipeVO> getSearchList(String searchName);

    public int saveRecipeFormData(RecipeVO recipeVo);

    public int insertRecipeImages(List<RecipeVO> imgList);

    public int insertRecipeSteps(List<RecipeVO> stepList);

    public int insertIngredients(List<RecipeVO> stepList);

    public  RecipeVO getRecipeDetailData(String recipeId);

}
