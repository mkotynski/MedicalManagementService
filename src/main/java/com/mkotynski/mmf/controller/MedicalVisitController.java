package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.MedicalVisit;
import com.mkotynski.mmf.repository.MedicalVisitRepository;
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
public class MedicalVisitController {

    private final MedicalVisitRepository medicalVisitRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "medical_visit";

    @GetMapping("/medical-visit")
    public List<MedicalVisit> getAllMedicalVisit() {
        return medicalVisitRepository.findAll();
    }

    @PostMapping("/medical-visit")
    public ResponseEntity<MedicalVisit> createMedicalVisit(@RequestBody MedicalVisit medicalVisit) throws URISyntaxException {
        log.debug("REST request to save medical-visit : {}", medicalVisit);

        MedicalVisit result = medicalVisitRepository.save(medicalVisit);
        return ResponseEntity.created(new URI("/api/medical-visit/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
