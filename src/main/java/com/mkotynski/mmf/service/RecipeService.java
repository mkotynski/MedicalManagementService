package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.dto.RecipeRequest;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.entity.MedicalVisit;
import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.entity.RecipePosition;
import com.mkotynski.mmf.repository.DoctorRepository;
import com.mkotynski.mmf.repository.PatientRepository;
import com.mkotynski.mmf.repository.RecipePositionRepository;
import com.mkotynski.mmf.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    RecipeRepository recipeRepository;
    RecipePositionRepository recipePositionRepository;
    RecipePositionService recipePositionService;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public Optional<RecipeResponse> getRecipe(Recipe recipe) {
        return Optional.ofNullable(recipe.getResponseDto());
    }

    public Optional<RecipeResponse> getRecipe(Integer id) {
        return Optional.ofNullable(recipeRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<RecipeResponse> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(e -> e.getResponseDto())
                .collect(Collectors.toList());
    }

    public Recipe saveRecipe(@RequestBody RecipeRequest recipeRequest) {
        Recipe def = new Recipe();
        if (recipeRequest.getId() != null) {
            Optional<Recipe> object = recipeRepository.findById(recipeRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDoctor(doctorRepository.findById(recipeRequest.getDoctor().getId()).orElse(null));
        def.setPatient(patientRepository.findById(recipeRequest.getPatient().getId()).orElse(null));
        def.setDate(new Date());
        def.setExpirationDate(recipeRequest.getExpirationDate());
        def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        Recipe finalRecipe = recipeRepository.save(def);
        Set<RecipePosition> positions = new HashSet<>();
        if(recipeRequest.getPositions() != null) {
            recipeRequest.getPositions().forEach(e -> {
                RecipePosition pos = new RecipePosition();
                pos.setRecipe(finalRecipe);
                pos.setDesription(e.getDescription());
                positions.add(recipePositionRepository.save(pos));
            });

            def.setRecipePositionSet(positions);
        } else def.setRecipePositionSet(null);

        return recipeRepository.save(def);
    }
}
