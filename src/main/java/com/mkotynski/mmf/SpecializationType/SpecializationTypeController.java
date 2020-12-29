package com.mkotynski.mmf.SpecializationType;


import com.mkotynski.mmf.SpecializationType.Dto.SpecializationTypeRequest;
import com.mkotynski.mmf.SpecializationType.Dto.SpecializationTypeResponse;
import com.mkotynski.mmf.Utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SpecializationTypeController {

    private final SpecializationTypeRepository specializationTypeRepository;
    private final SpecializationTypeService specializationTypeService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "specialization_type";

    @GetMapping("/specialization-type")
    public List<SpecializationTypeResponse> getAllSpecializationTypes() {
        return specializationTypeService.getAllSpecializationType();
    }

    @GetMapping("/specialization-type/{id}")
    public ResponseEntity<Optional<SpecializationTypeResponse>> getSpecializationType(@PathVariable Integer id) {
        log.debug("REST request to read specialization-type");

        return ResponseEntity.ok().body(specializationTypeService.getSpecializationType(id));
    }

    @PostMapping("/specialization-type")
    public ResponseEntity<SpecializationTypeResponse> createSpecializationType(@RequestBody SpecializationTypeRequest specializationTypeRequest) throws URISyntaxException {
        log.debug("REST request to save specialization type : {}", specializationTypeRequest);

        SpecializationType specializationType = specializationTypeService.saveSpecializationType(specializationTypeRequest);
        SpecializationTypeResponse result = specializationTypeService.getSpecializationType(specializationType).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/specialization-type")
    public ResponseEntity<SpecializationTypeResponse> updateVisitType(@RequestBody SpecializationTypeRequest specializationTypeRequest) throws URISyntaxException {
        log.debug("REST request to update specialization-type : {}", specializationTypeRequest);

        SpecializationType specializationType = specializationTypeService.saveSpecializationType(specializationTypeRequest);
        SpecializationTypeResponse result = specializationTypeService.getSpecializationType(specializationType).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/specialization-type/{id}")
    public ResponseEntity<Void> deleteVisitType(@PathVariable Integer id) {
        log.debug("REST request to delete specialization-type : {}", id);
        specializationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
