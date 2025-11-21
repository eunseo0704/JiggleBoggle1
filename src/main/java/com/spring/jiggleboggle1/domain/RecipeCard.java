package com.spring.jiggleboggle1.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor    // ⭐ 기본 생성자 자동 생성
@AllArgsConstructor   // ⭐ (title, description) 생성자 자동 생성
public class RecipeCard {
    private String title;
    private String description;
}
