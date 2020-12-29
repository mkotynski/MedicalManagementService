package com.mkotynski.mmf.AvailableDate;


import com.mkotynski.mmf.AvailableDate.Dto.AvailableDateRequest;
import com.mkotynski.mmf.AvailableDate.Dto.AvailableDateResponse;
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
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AvailableDateController {

    private final AvailableDateService availableDateService;
    private final AvailableDateRepository availableDateRepository;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "available_date";
    private static final String resource = "available-date";

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @GetMapping(resource)
    public List<AvailableDateResponse> getAllAvailableDates() {
        log.debug("REST request to read all available dates");

        return availableDateService.getAllAvailableDates();
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @GetMapping(resource+"/{id}")
    public ResponseEntity<Optional<AvailableDateResponse>> getAvailableDate(@PathVariable Integer id) {
        log.debug("REST request to all available dates");

        return ResponseEntity.ok().body(availableDateService.getAvailableDate(id));
    }


    @PreAuthorize("hasAnyRole('ROLE_doctor')")
    @PostMapping(resource)
    public ResponseEntity<AvailableDateResponse> createAvailableDate(@RequestBody AvailableDateRequest availableDateRequest) throws URISyntaxException {
        log.debug("REST request to create doctor : {}", availableDateRequest);

        AvailableDate availableDate = availableDateService.saveAvailableDate(availableDateRequest);
        AvailableDateResponse result = availableDateService.getAvailableDate(availableDate).get();
        return ResponseEntity.created(new URI("/api/" + resource + "/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor', 'ROLE_patient')")
    @PutMapping(resource)
    public ResponseEntity<AvailableDateResponse> updateAvailableDate(@RequestBody AvailableDateRequest availableDateRequest) throws URISyntaxException {
        log.debug("REST request to update doctor : {}", availableDateRequest);

        AvailableDate availableDate = availableDateService.updateAvailableDate(availableDateRequest);
        AvailableDateResponse result = availableDateService.getAvailableDate(availableDate).get();
        return ResponseEntity.created(new URI("/api/" + resource + "/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor')")
    @DeleteMapping(resource + "/{id}")
    public ResponseEntity<Void> deleteAvailableDate(@PathVariable Integer id) {
        log.debug("REST request to delete doctor : {}", id);
        availableDateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_admin', 'ROLE_patient', 'ROLE_doctor')")
    @GetMapping(resource+"/by-doctor/{id}")
    public ResponseEntity<Object> getAllAvailableDatesByDoctorId(@PathVariable Integer id) {
        log.debug("REST request to read all available-dates by doctor id");
        JSONObject entity = new JSONObject();

            entity.put("results", availableDateService.getAllAvailableDatesByDoctorId(id));

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    /** DOCTOR API's **/

    @PreAuthorize("hasAnyRole('ROLE_doctor')")
    @GetMapping(resource+"/doctor/find-all-available-dates")
    public ResponseEntity<Object> getAllAvailableDatesByDoctorId(ServletRequest request) {
        log.debug("REST request to read all available-dates by doctor id");
        JSONObject entity = new JSONObject();

        entity.put("results", availableDateService.getAllAvailableDatesByDoctorId(SubjectUtil.getSubjectFromRequest(request)));

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_doctor')")
    @PostMapping(resource+"/by-doctor")
    public ResponseEntity<AvailableDateResponse> createAvailableDate(@RequestBody AvailableDateRequest availableDateRequest, ServletRequest request) throws URISyntaxException {
        log.debug("REST request to create doctor : {}", availableDateRequest);

        AvailableDate availableDate = availableDateService.saveAvailableDate(availableDateRequest, SubjectUtil.getSubjectFromRequest(request));
        AvailableDateResponse result = availableDateService.getAvailableDate(availableDate).get();
        return ResponseEntity.created(new URI("/api/" + resource + "/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
