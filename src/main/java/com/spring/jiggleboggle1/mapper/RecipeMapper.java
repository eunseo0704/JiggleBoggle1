package com.spring.jiggleboggle1.mapper;

import com.spring.jiggleboggle1.domain.RecipeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    public List<RecipeVO> getRecipeList();
}
