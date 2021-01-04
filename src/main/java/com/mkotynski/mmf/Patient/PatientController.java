package com.mkotynski.mmf.Patient;


import com.mkotynski.mmf.Patient.Dto.PatientRequest;
import com.mkotynski.mmf.Patient.Dto.PatientResponse;
import com.mkotynski.mmf.Utils.HeaderUtil;
import com.mkotynski.mmf.Utils.SubjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PatientController {


    private final PatientService patientService;
    private final PatientRepository patientRepository;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "patient";

    @GetMapping("/patient")
    public List<PatientResponse> getAllPatients() {
        log.debug("REST request to read all patient");

        return patientService.getAllPatients();
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Optional<PatientResponse>> getPatient(@PathVariable Integer id) {
        log.debug("REST request to read doctor");

        return ResponseEntity.ok().body(patientService.getPatient(id));
    }

    @PostMapping("/patient")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest) throws URISyntaxException {
        log.debug("REST request to create patient : {}", patientRequest);

        Patient patient = patientService.savePatient(patientRequest);
        PatientResponse result = patientService.getPatient(patient).get();
        return ResponseEntity.created(new URI("/api/patient/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/patient")
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody PatientRequest patientRequest) throws URISyntaxException {
        log.debug("REST request to update patient : {}", patientRequest);

        Patient patient = patientService.savePatient(patientRequest);
        PatientResponse result = patientService.getPatient(patient).get();
        return ResponseEntity.created(new URI("/api/patient/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
        log.debug("REST request to delete patient : {}", id);
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/patient/logged")
    public ResponseEntity<Optional<PatientResponse>> getPatientLogged(ServletRequest request) {
        log.debug("REST request to read patient");

        return ResponseEntity.ok().body(patientService.getPatientBySubject(SubjectUtil.getSubjectFromRequest(request)));
    }

}
