package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePositionResponse {
    private Integer id;
    private String description;
    private RecipeResponse recipe;
}
