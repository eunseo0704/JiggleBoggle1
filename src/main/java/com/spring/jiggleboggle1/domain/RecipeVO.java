package com.spring.jiggleboggle1.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RecipeVO {

    private String recipeId;
    private int seq;
    private String userId;
    private String categoryId;
    private String title;
    private String rcpDisc;
    private String cookTime;
    private String cookDfct;
    private String rgtDate;
    private String updDate;
    private String searchName;
    private String tagName;
    private String recipeTips;

    private String ingrId;
    private String[] ingrNames;
    private String ingrName;
    private String[] ingrAmounts;
    private String ingrAmount;
    private String[] ingrUnits;
    private String ingrUnit;

    private String imagePath;
    private int imageId;

    List<MultipartFile> mainImages;
    List<MultipartFile> stepImages;

    private int stepOrder;
    private String stepDescription;
    private String[] stepDescriptions;
    private String stepImagePath;

    List<RecipeVO> imageList; // 이미지 리스트
    List<RecipeVO> stepList; //단계리스트
    List<RecipeVO> ingrList; //재료 리스트

}
