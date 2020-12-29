package com.mkotynski.mmf.Recipe.RecipePosition.Dto;

import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePositionRequest {
    private Integer id;
    private String description;
    private RecipeResponse recipe;
}
