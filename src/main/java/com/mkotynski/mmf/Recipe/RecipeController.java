package com.mkotynski.mmf.Recipe;


import com.mkotynski.mmf.Recipe.Dto.RecipeRequest;
import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import com.mkotynski.mmf.Utils.HeaderUtil;
import com.mkotynski.mmf.Utils.SubjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {


    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "recipe";
    private static final String resource = "recipe";

    @GetMapping(resource)
    public List<RecipeResponse> getAllRecipes() {
        log.debug("REST request to read all medical-visits");

        return recipeService.getAllRecipes();
    }

    @GetMapping(resource + "/{id}")
    public ResponseEntity<Optional<RecipeResponse>> getRecipe(@PathVariable Integer id) {
        log.debug("REST request to read medical-visit");

        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }


    @PostMapping(resource)
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeRequest recipeRequest) throws URISyntaxException {
        log.debug("REST request to create medical-visit : {}", recipeRequest);

        Recipe recipe = recipeService.saveRecipe(recipeRequest);
        RecipeResponse result = recipeService.getRecipe(recipe).get();
        return ResponseEntity.created(new URI("/api/recipe/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping(resource)
    public ResponseEntity<RecipeResponse> updateRecipe(@RequestBody RecipeRequest recipeRequest) throws URISyntaxException {
        log.debug("REST request to create recipe : {}", recipeRequest);

        Recipe recipe = recipeService.saveRecipe(recipeRequest);
        RecipeResponse result = recipeService.getRecipe(recipe).get();
        return ResponseEntity.created(new URI("/api/recipe/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping(resource + "/{id}")
    public ResponseEntity<Void> deleteMedicalVisit(@PathVariable Integer id) {
        log.debug("REST request to delete medical-visit : {}", id);
        recipeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(resource + "/by-visit-id/{id}")
    public ResponseEntity<List<RecipeResponse>> getReferencesByVisitId(@PathVariable Integer id) {
        log.debug("REST request to read all references");

        return ResponseEntity.ok().body(recipeService.getRecipesByVisitId(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_patient')")
    @GetMapping(resource + "/of-subject")
    public ResponseEntity<List<RecipeResponse>> getReferencesOfSubject(ServletRequest request) {
        log.debug("REST request to read medical-visit for doctor or patient subject");
        return ResponseEntity.ok().body(recipeService.getReferencesOfSubject(SubjectUtil.getSubjectFromRequest(request)));
    }

}
