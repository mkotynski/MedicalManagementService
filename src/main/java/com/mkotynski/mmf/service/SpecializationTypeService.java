package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.*;
import com.mkotynski.mmf.entity.SpecializationType;
import com.mkotynski.mmf.entity.VisitType;
import com.mkotynski.mmf.repository.SpecializationTypeRepository;
import com.mkotynski.mmf.repository.VisitTypeRepository;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecializationTypeService {


    SpecializationTypeRepository specializationTypeRepository;

    public Optional<SpecializationTypeResponse> getSpecializationType(SpecializationType specializationType) {
        return Optional.ofNullable(specializationType.getResponseDto());
    }

    public Optional<SpecializationTypeResponse> getSpecializationType(Integer id) {
        return Optional.ofNullable(specializationTypeRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<SpecializationTypeResponse> getAllSpecializationType() {
        return specializationTypeRepository.findAll()
                .stream()
                .map(SpecializationType::getResponseDto)
                .collect(Collectors.toList());
    }

    public SpecializationType saveSpecializationType(@RequestBody SpecializationTypeRequest specializationTypeRequest) {
        SpecializationType def = new SpecializationType();
        if (specializationTypeRequest.getId() != null) {
            Optional<SpecializationType> object = specializationTypeRepository.findById(specializationTypeRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setName(specializationTypeRequest.getName());

        return specializationTypeRepository.save(def);
    }

}
