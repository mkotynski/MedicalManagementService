package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.Patient;
import com.mkotynski.mmf.repository.DoctorRepository;
import com.mkotynski.mmf.repository.PatientRepository;
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
public class PatientController {

    private final PatientRepository patientRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "patient";

    @GetMapping("/patient")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @PostMapping("/patient")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) throws URISyntaxException {
        log.debug("REST request to save patient : {}", patient);

        Patient result = patientRepository.save(patient);
        return ResponseEntity.created(new URI("/api/patient/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
