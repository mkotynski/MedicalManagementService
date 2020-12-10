package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.dto.PatientRequest;
import com.mkotynski.mmf.dto.PatientResponse;
import com.mkotynski.mmf.dto.VisitTypeRequest;
import com.mkotynski.mmf.dto.VisitTypeResponse;
import com.mkotynski.mmf.entity.Patient;
import com.mkotynski.mmf.entity.VisitType;
import com.mkotynski.mmf.repository.VisitTypeRepository;
import com.mkotynski.mmf.service.VisitTypeService;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class VisitTypeController {

    private final VisitTypeRepository visitTypeRepository;
    private final VisitTypeService visitTypeService;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "visit-type";

    @GetMapping("/visit-type")
    public List<VisitTypeResponse> getAllVisitTypes() {
        return visitTypeService.getAllVisitTypes();
    }

    @PostMapping("/visit-type")
    public ResponseEntity<VisitTypeResponse> createVisitType(@RequestBody VisitTypeRequest visitTypeRequest) throws URISyntaxException {
        log.debug("REST request to save visitType : {}", visitTypeRequest);

        VisitType visitType = visitTypeService.saveVisitType(visitTypeRequest);
        VisitTypeResponse result = visitTypeService.getVisitType(visitType).get();
        return ResponseEntity.created(new URI("/api/patient/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/visit-type")
    public ResponseEntity<VisitTypeResponse> updateVisitType(@RequestBody VisitTypeRequest visitTypeRequest) throws URISyntaxException {
        log.debug("REST request to update visit-type : {}", visitTypeRequest);

        VisitType visitType = visitTypeService.saveVisitType(visitTypeRequest);
        VisitTypeResponse result = visitTypeService.getVisitType(visitType).get();
        return ResponseEntity.created(new URI("/api/patient/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/visit-type/{id}")
    public ResponseEntity<Void> deleteVisitType(@PathVariable Integer id) {
        log.debug("REST request to delete visit-type : {}", id);
        visitTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
