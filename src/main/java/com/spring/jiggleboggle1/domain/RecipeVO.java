package com.spring.jiggleboggle1.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeVO {

    private String recipeId;
    private String userId;
    private String categoryId;
    private String title;
    private String rcpDisc;
    private String cookTime;
    private String cookDfct;
    private String rgtDate;
    private String updDate;
    private String imgUrl;
    private String status;
    private String searchName;
    private String tagName;

    private String[] ingrNames;
    private String ingrName;
    private String[] ingrAmounts;
    private String ingrAmount;
    private String[] ingrUnits;
    private String ingrUnit;

}
