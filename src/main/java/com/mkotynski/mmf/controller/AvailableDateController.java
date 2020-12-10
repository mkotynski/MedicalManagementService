package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.dto.AvailableDateRequest;
import com.mkotynski.mmf.dto.AvailableDateResponse;
import com.mkotynski.mmf.entity.AvailableDate;
import com.mkotynski.mmf.repository.AvailableDateRepository;
import com.mkotynski.mmf.service.AvailableDateService;
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
public class AvailableDateController {

    private final AvailableDateService availableDateService;
    private final AvailableDateRepository availableDateRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "available_date";
    private static final String resource = "available-date";

    @GetMapping(resource)
    public List<AvailableDateResponse> getAllAvailableDates() {
        log.debug("REST request to read all available dates");

        return availableDateService.getAllAvailableDates();
    }

    @PostMapping(resource)
    public ResponseEntity<AvailableDateResponse> createDoctor(@RequestBody AvailableDateRequest availableDateRequest) throws URISyntaxException {
        log.debug("REST request to create doctor : {}", availableDateRequest);

        AvailableDate availableDate = availableDateService.saveAvailableDate(availableDateRequest);
        AvailableDateResponse result = availableDateService.getAvailableDate(availableDate).get();
        return ResponseEntity.created(new URI("/api/" + resource + "/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);

    }

    @PutMapping(resource)
    public ResponseEntity<AvailableDateResponse> updateDoctor(@RequestBody AvailableDateRequest availableDateRequest) throws URISyntaxException {
        log.debug("REST request to update doctor : {}", availableDateRequest);

        AvailableDate availableDate = availableDateService.saveAvailableDate(availableDateRequest);
        AvailableDateResponse result = availableDateService.getAvailableDate(availableDate).get();
        return ResponseEntity.created(new URI("/api/" + resource + "/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping(resource + "/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        log.debug("REST request to delete doctor : {}", id);
        availableDateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
