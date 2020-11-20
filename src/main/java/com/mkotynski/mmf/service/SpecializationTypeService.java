package com.mkotynski.mmf.service;


import com.mkotynski.mmf.entity.SpecializationType;
import com.mkotynski.mmf.repository.SpecializationTypeRepository;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@AllArgsConstructor
public class SpecializationTypeService {

    private final SpecializationTypeRepository specializationTypeRepository;

    @Value("${pl.mkotynski.wms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "specialization_type";

    @GetMapping("/specialization-type")
    public List<SpecializationType> getAllSpecializationTypes() {
        return specializationTypeRepository.findAll();
    }

    @PostMapping("/specialization-type")
    public ResponseEntity<SpecializationType> createSpecializationType(@RequestBody SpecializationType specializationType) throws URISyntaxException {
        log.debug("REST request to save dcotor : {}", specializationType);

        SpecializationType result = specializationTypeRepository.save(specializationType);
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
