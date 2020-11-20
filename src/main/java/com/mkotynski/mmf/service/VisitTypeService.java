package com.mkotynski.mmf.service;


import com.mkotynski.mmf.entity.VisitType;
import com.mkotynski.mmf.repository.VisitTypeRepository;
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
public class VisitTypeService {

    private final VisitTypeRepository visitTypeRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "visit-type";

    @GetMapping("/visit-type")
    public List<VisitType> getAllVisitTypes() {
        return visitTypeRepository.findAll();
    }

    @PostMapping("/visit-type")
    public ResponseEntity<VisitType> createDoctor(@RequestBody VisitType visitType) throws URISyntaxException {
        log.debug("REST request to save visitType : {}", visitType);

        VisitType result = visitTypeRepository.save(visitType);
        return ResponseEntity.created(new URI("/api/definitions/accepted-pallets/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }




}
