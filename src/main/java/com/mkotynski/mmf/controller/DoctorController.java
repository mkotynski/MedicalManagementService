package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.repository.DoctorRepository;
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
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "doctor";

    @GetMapping("/doctor")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @PostMapping("/doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) throws URISyntaxException {
        log.debug("REST request to save doctor : {}", doctor);

        Doctor result = doctorRepository.save(doctor);
        return ResponseEntity.created(new URI("/api/doctor/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
