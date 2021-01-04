package com.mkotynski.mmf.Recipe;


import com.mkotynski.mmf.MedicalVisit.MedicalVisitRepository;
import com.mkotynski.mmf.Receipt.ReceiptPosition.ReceiptPosition;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionService;
import com.mkotynski.mmf.Recipe.Dto.RecipeRequest;
import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePosition;
import com.mkotynski.mmf.Doctor.DoctorRepository;
import com.mkotynski.mmf.Patient.PatientRepository;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionRepository;
import com.mkotynski.mmf.Reference.Dto.ReferenceResponse;
import com.mkotynski.mmf.Reference.Reference;
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
    MedicalVisitRepository medicalVisitRepository;

    public Optional<RecipeResponse> getRecipe(Recipe recipe) {
        return Optional.ofNullable(recipe.getResponseDto());
    }

    public Optional<RecipeResponse> getRecipe(Integer id) {
        return Optional.ofNullable(recipeRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<RecipeResponse> getRecipesByVisitId(Integer id) {
        return recipeRepository.findAllByMedicalVisit_Id(id)
                .stream()
                .map(Recipe::getResponseDto)
                .collect(Collectors.toList());
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
        def.setMedicalVisit(medicalVisitRepository.findById(recipeRequest.getVisit().getId()).orElse(null));
        def.setExpirationDate(recipeRequest.getExpirationDate());
        if(recipeRequest.getCode() == null) def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        def = recipeRepository.save(def);

        Recipe finalRecipe = recipeRepository.save(def);
        Set<RecipePosition> positions = new HashSet<>();
        if(recipeRequest.getPositions() != null) {
            recipeRequest.getPositions().forEach(e -> {
                positions.add(recipePositionService.saveRecipePosition(e, finalRecipe.getId()));
            });

            def.setRecipePositionSet(positions);
        } else def.setRecipePositionSet(null);

        return recipeRepository.save(def);
    }

    public List<RecipeResponse> getReferencesOfSubject(String subjectFromRequest) {
        return recipeRepository.findAllByMedicalVisit_Patient_Subject(subjectFromRequest)
                .stream()
                .map(Recipe::getResponseDto)
                .collect(Collectors.toList());
    }
}
