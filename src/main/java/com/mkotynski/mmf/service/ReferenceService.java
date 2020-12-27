package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.RecipeRequest;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.dto.ReferenceRequest;
import com.mkotynski.mmf.dto.ReferenceResponse;
import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.entity.RecipePosition;
import com.mkotynski.mmf.entity.Reference;
import com.mkotynski.mmf.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReferenceService {

    ReferenceRepository referenceRepository;
    RecipePositionRepository recipePositionRepository;
    RecipePositionService recipePositionService;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public Optional<ReferenceResponse> getReference(Reference reference) {
        return Optional.ofNullable(reference.getResponseDto());
    }

    public Optional<ReferenceResponse> getReference(Integer id) {
        return Optional.ofNullable(referenceRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<ReferenceResponse> getAllReferences() {
        return referenceRepository.findAll()
                .stream()
                .map(Reference::getResponseDto)
                .collect(Collectors.toList());
    }

    public Reference saveReference(@RequestBody ReferenceRequest referenceRequest) {
        Reference def = new Reference();
        if (referenceRequest.getId() != null) {
            Optional<Reference> object = referenceRepository.findById(referenceRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDoctor(doctorRepository.findById(referenceRequest.getDoctor().getId()).orElse(null));
        def.setPatient(patientRepository.findById(referenceRequest.getPatient().getId()).orElse(null));
        def.setDate(new Date());
        def.setExpirationDate(referenceRequest.getExpirationDate());
        def.setDetails(referenceRequest.getDetails());
        def.setReferenceTo(referenceRequest.getReferenceTo());
        def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        return referenceRepository.save(def);
    }
}
