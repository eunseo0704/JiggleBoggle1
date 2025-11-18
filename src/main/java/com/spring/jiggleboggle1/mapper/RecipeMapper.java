package com.spring.jiggleboggle1.mapper;

import com.spring.jiggleboggle1.domain.RecipeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    public List<RecipeVO> getImgList(String recipeId);

    public List<RecipeVO> getStepList(String recipeId);

    public List<RecipeVO> getIngrList(String recipeId);

    public Integer checkUserView(@Param("recipeId") String recipeId, @Param("userId") String userId);

    public int insetUserView(@Param("recipeId") String recipeId, @Param("userId") String userId);

    public int updateUserView(@Param("recipeId") String recipeId);

}
