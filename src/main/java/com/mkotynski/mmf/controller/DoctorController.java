package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.repository.DoctorRepository;
import com.mkotynski.mmf.service.DoctorService;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "doctor";

    @GetMapping("/doctor")
    public List<DoctorResponse> getAllDoctors() {
        log.debug("REST request to read all doctor");

        return doctorService.getAllDoctors();
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Optional<DoctorResponse>> getDoctor(@PathVariable Integer id) {
        log.debug("REST request to read doctor");

        return ResponseEntity.ok().body(doctorService.getDoctor(id));
    }

    @PostMapping("/doctor")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest doctorRequest) throws URISyntaxException {
        log.debug("REST request to create doctor : {}", doctorRequest);

        Doctor doctor = doctorService.saveDoctor(doctorRequest);
        DoctorResponse result = doctorService.getDoctor(doctor).get();
        return ResponseEntity.created(new URI("/api/doctor/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);

    }

    @PutMapping("/doctor")
    public DoctorResponse updateDoctor(@RequestBody DoctorRequest doctorRequest) throws URISyntaxException {
        log.debug("REST request to update doctor : {}", doctorRequest);

        Doctor doctor = doctorService.saveDoctor(doctorRequest);
        return doctorService.getDoctor(doctor).get();
    }

    @DeleteMapping("/doctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        log.debug("REST request to delete doctor : {}", id);
        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
