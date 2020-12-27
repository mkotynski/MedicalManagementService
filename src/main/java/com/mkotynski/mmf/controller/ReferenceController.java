package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.dto.RecipeRequest;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.dto.ReferenceRequest;
import com.mkotynski.mmf.dto.ReferenceResponse;
import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.entity.Reference;
import com.mkotynski.mmf.repository.RecipeRepository;
import com.mkotynski.mmf.repository.ReferenceRepository;
import com.mkotynski.mmf.service.RecipeService;
import com.mkotynski.mmf.service.ReferenceService;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReferenceController {


    private final ReferenceRepository referenceRepository;
    private final ReferenceService referenceService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "reference";
    private static final String resource = "reference";

    @GetMapping(resource)
    public List<ReferenceResponse> getAllReferences() {
        log.debug("REST request to read all references");

        return referenceService.getAllReferences();
    }

    @GetMapping(resource + "/{id}")
    public ResponseEntity<Optional<ReferenceResponse>> getReference(@PathVariable Integer id) {
        log.debug("REST request to read reference");

        return ResponseEntity.ok().body(referenceService.getReference(id));
    }


    @PostMapping(resource)
    public ResponseEntity<ReferenceResponse> createReference(@RequestBody ReferenceRequest referenceRequest) throws URISyntaxException {
        log.debug("REST request to create reference : {}", referenceRequest);

        Reference reference = referenceService.saveReference(referenceRequest);
        ReferenceResponse result = referenceService.getReference(reference).orElse(null);
        return ResponseEntity.created(new URI("/api/reference/" + (result != null ? result.getId() : null)))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, (result != null ? result.getId().toString() : null)))
                .body(result);
    }

    @PutMapping(resource)
    public ResponseEntity<ReferenceResponse> updateReference(@RequestBody ReferenceRequest referenceRequest) throws URISyntaxException {
        log.debug("REST request to create recipe : {}", referenceRequest);

        Reference reference = referenceService.saveReference(referenceRequest);
        ReferenceResponse result = referenceService.getReference(reference).orElse(null);
        return ResponseEntity.created(new URI("/api/reference/" + (result != null ? result.getId() : null)))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, (result != null ? result.getId().toString() : null)))
                .body(result);
    }

    @DeleteMapping(resource + "/{id}")
    public ResponseEntity<Void> deleteReference(@PathVariable Integer id) {
        log.debug("REST request to delete medical-visit : {}", id);
        referenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
