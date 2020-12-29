package com.mkotynski.mmf.Recipe.RecipePosition;


import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionRequest;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionResponse;
import com.mkotynski.mmf.Recipe.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipePositionService {

    RecipeRepository recipeRepository;
    RecipePositionRepository recipePositionRepository;

    public Optional<RecipePositionResponse> getRecipe(RecipePosition recipePosition) {
        return Optional.ofNullable(recipePosition.getResponseDto());
    }

    public Optional<RecipePositionResponse> getRecipe(Integer id) {
        return Optional.ofNullable(recipePositionRepository.findById(id).orElse(null).getResponseDto());
    }
    public RecipePosition saveRecipePosition(@RequestBody RecipePositionRequest recipePositionRequest, Integer recipeId) {
        RecipePosition def = new RecipePosition();
        if (recipePositionRequest.getId() != null) {
            Optional<RecipePosition> object = recipePositionRepository.findById(recipePositionRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDescription(recipePositionRequest.getDescription());
        def.setRecipe(recipeRepository.findById(recipeId).orElse(null));
        return recipePositionRepository.save(def);
    }

}
