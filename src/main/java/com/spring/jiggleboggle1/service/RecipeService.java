package com.spring.jiggleboggle1.service;

import com.spring.jiggleboggle1.domain.RecipeVO;
import com.spring.jiggleboggle1.mapper.RecipeMapper;
import com.spring.jiggleboggle1.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecipeService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final RecipeMapper recipeMapper;

    public List<RecipeVO> getRecipeList() {
        return recipeMapper.getRecipeList();
    }

    public List<RecipeVO> getSearchList(String searchName)  {
        return recipeMapper.getSearchList(searchName);
    }

    public List<RecipeVO> getMyRecipes(String userId)  {
        return recipeMapper.getMyRecipes(userId);
    }

    @Transactional
    public int  saveRecipeFormData(RecipeVO recipeVo) {

        int result;

//recipeId 생성
        String datePart = new SimpleDateFormat("yyMMdd").format(new Date());
        int randomPart = new Random().nextInt(900) + 100; // 3자리 랜덤
        String recipeId = "RCP" + datePart + randomPart;

        recipeVo.setRecipeId(recipeId);

        //form Data 저장 - 완료
        result = recipeMapper.saveRecipeFormData(recipeVo);

        // 메인 이미지 저장 - 완료
        List<String> mainImagePaths = FileUploadUtil.saveRecipeFiles(recipeVo.getMainImages(), uploadDir, recipeId);

        if (!mainImagePaths.isEmpty()) {
            List<RecipeVO> imgList = new ArrayList<>();

            for (int i = 0; i < mainImagePaths.size(); i++) {
                RecipeVO mainImgVo = new RecipeVO();

                mainImgVo.setRecipeId(recipeId);
                mainImgVo.setSeq(i + 1);
                mainImgVo.setImagePath(mainImagePaths.get(i));
                imgList.add(mainImgVo);
            }
            if(!imgList.isEmpty()) {
                result = recipeMapper.insertRecipeImages(imgList);
            }
        }

        // 3️⃣ 조리 단계 저장
        List<String> stepImagePaths = FileUploadUtil.saveRecipeFiles(recipeVo.getStepImages(), uploadDir, recipeId+"_step");

        List<RecipeVO> stepList = new ArrayList<>();
        for (int i = 0; i < recipeVo.getStepDescriptions().length; i++) {
            RecipeVO step = new RecipeVO();
            step.setRecipeId(recipeId);
            step.setStepOrder(i + 1);
            step.setStepDescription(recipeVo.getStepDescriptions()[i]);

            if (i < stepImagePaths.size()) {
                step.setStepImagePath(stepImagePaths.get(i));
            }
            stepList.add(step);
        }
        if (!stepList.isEmpty()) {
            result = recipeMapper.insertRecipeSteps(stepList);
        }

        // 4️⃣ 재료 저장 - 완료
        List<RecipeVO> ingList = new ArrayList<>();
        for (int i = 0; i < recipeVo.getIngrNames().length; i++) {

            RecipeVO ing = new RecipeVO();
            ing.setRecipeId(recipeId);
            ing.setSeq(i + 1);
            ing.setIngrName(recipeVo.getIngrNames()[i]);
            ing.setIngrAmount(recipeVo.getIngrAmounts()[i]);
            ing.setIngrUnit(recipeVo.getIngrUnits()[i]);
            ingList.add(ing);
        }

        if (!ingList.isEmpty()) {
            result = recipeMapper.insertIngredients(ingList);
        }

        return result;

    }

    public RecipeVO getRecipeDetailData(String recipeId, String userId) {
        int result;

        RecipeVO recipe = new RecipeVO();

        //userId가 있을때
        if (userId != null && !userId.isEmpty()) {

            //유저 이미 조회했는지 확인
            Integer exists = recipeMapper.checkUserView(recipeId, userId);

            if (exists != null && exists > 0) {
                recipe = recipeMapper.getRecipeDetailData(recipeId);
                recipe.setImageList(recipeMapper.getImgList(recipeId));
                recipe.setStepList(recipeMapper.getStepList(recipeId));
                recipe.setIngrList(recipeMapper.getIngrList(recipeId));

                return recipe;

            }else{
                // 실제 조회수 추가
                result = recipeMapper.insetUserView(recipeId, userId);
                result = recipeMapper.updateUserView(recipeId);

            }

        }

        recipe = recipeMapper.getRecipeDetailData(recipeId);
        recipe.setImageList(recipeMapper.getImgList(recipeId));
        recipe.setStepList(recipeMapper.getStepList(recipeId));
        recipe.setIngrList(recipeMapper.getIngrList(recipeId));

        return recipe;
    }



}
