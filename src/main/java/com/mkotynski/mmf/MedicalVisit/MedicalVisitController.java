package com.mkotynski.mmf.MedicalVisit;


import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitRequest;
import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitResponse;
import com.mkotynski.mmf.MedicalVisit.Dto.VisitWithDetailsRequest;
import com.mkotynski.mmf.MedicalVisit.Dto.VisitWithDetailsResponse;
import com.mkotynski.mmf.Utils.HeaderUtil;
import com.mkotynski.mmf.Utils.SubjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
public class MedicalVisitController {

    private final MedicalVisitService medicalVisitService;
    private final MedicalVisitRepository medicalVisitRepository;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "medical-visit";

    @GetMapping("/medical-visit")
    public List<MedicalVisitResponse> getMedicalVisits() {
        log.debug("REST request to read all medical-visits");

        return medicalVisitService.getAllMedicalVisit();
    }

    @GetMapping("/medical-visit/{id}")
    public ResponseEntity<Optional<MedicalVisitResponse>> getMedicalVisit(@PathVariable Integer id) {
        log.debug("REST request to read medical-visit");

        return ResponseEntity.ok().body(medicalVisitService.getMedicalVisit(id));
    }


    @PostMapping("/medical-visit")
    public ResponseEntity<MedicalVisitResponse> createDoctor(@RequestBody MedicalVisitRequest medicalVisitRequest) throws URISyntaxException {
        log.debug("REST request to create medical-visit : {}", medicalVisitRequest);

        MedicalVisit medicalVisit = medicalVisitService.saveMedicalVisit(medicalVisitRequest);
        MedicalVisitResponse result = medicalVisitService.getMedicalVisit(medicalVisit).get();
        return ResponseEntity.created(new URI("/api/medical-visit/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/medical-visit")
    public ResponseEntity<MedicalVisitResponse> updateDoctor(@RequestBody MedicalVisitRequest medicalVisitRequest) throws URISyntaxException {
        log.debug("REST request to update medical-visit : {}", medicalVisitRequest);

        MedicalVisit medicalVisit = medicalVisitService.saveMedicalVisit(medicalVisitRequest);
        MedicalVisitResponse result = medicalVisitService.getMedicalVisit(medicalVisit).get();
        return ResponseEntity.created(new URI("/api/medical-visit/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/medical-visit/{id}")
    public ResponseEntity<Void> deleteMedicalVisit(@PathVariable Integer id) {
        log.debug("REST request to delete medical-visit : {}", id);
        medicalVisitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/medical-visit/by-patient/{id}")
    public ResponseEntity<Object> getAllMedicalVisitByPatientId(@PathVariable Integer id) {
        log.debug("REST request to read all medical-visits by patient id");
        JSONObject entity = new JSONObject();

        entity.put("results", medicalVisitService.getAllMedicalVisitByPatientId(id));

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @GetMapping("/medical-visit/find-all-medical-visit")
    public ResponseEntity<Object> getAllMedicalVisitOfSubject(ServletRequest request) {
        log.debug("REST request to read all medical-visits of subject");
        JSONObject entity = new JSONObject();

        entity.put("results", medicalVisitService.getAllMedicalVisit(SubjectUtil.getSubjectFromRequest(request)));

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @GetMapping("/medical-visit/find-medical-visit/{id}")
    public ResponseEntity<Object> getMedicalVisitOfSubject(@PathVariable Integer id, ServletRequest request) {
        log.debug("REST request to read medical-visit for doctor or patient subject");
        return ResponseEntity.ok().body(medicalVisitService.getMedicalVisit(id, SubjectUtil.getSubjectFromRequest(request)));
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor')")
    @PutMapping("/medical-visit/save-visit-with-details")
    public ResponseEntity<VisitWithDetailsResponse> saveVisitWithDetails(@RequestBody VisitWithDetailsRequest visitWithDetailsRequest, ServletRequest request) throws URISyntaxException {
        log.debug("REST request to update visit with details: {}", visitWithDetailsRequest);

        VisitWithDetailsResponse visitWithDetails = medicalVisitService.saveVisitWithDetails(visitWithDetailsRequest, SubjectUtil.getSubjectFromRequest(request));
        return ResponseEntity.created(new URI("/api/medical-visit/save-visit-with-details"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, visitWithDetails.getMedicalVisit().getId().toString()))
                .body(visitWithDetails);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @GetMapping("/medical-visit/find-medical-visit-by-status-done")
    public ResponseEntity<List<MedicalVisitResponse>> getVisitsHistoryOfSubject(ServletRequest request) {
        log.debug("REST request to read medical-visit for doctor or patient subject");
        return ResponseEntity.ok().body(medicalVisitService.getVisitHistoryOfSubject(SubjectUtil.getSubjectFromRequest(request)));
    }


}
