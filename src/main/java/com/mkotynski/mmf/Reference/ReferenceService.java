package com.mkotynski.mmf.Reference;


import com.mkotynski.mmf.Doctor.DoctorRepository;
import com.mkotynski.mmf.MedicalVisit.MedicalVisitRepository;
import com.mkotynski.mmf.Patient.PatientRepository;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionRepository;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionService;
import com.mkotynski.mmf.Reference.Dto.ReferenceRequest;
import com.mkotynski.mmf.Reference.Dto.ReferenceResponse;
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
    MedicalVisitRepository medicalVisitRepository;

    public Optional<ReferenceResponse> getReference(Reference reference) {
        return Optional.ofNullable(reference.getResponseDto());
    }

    public Optional<ReferenceResponse> getReference(Integer id) {
        return Optional.ofNullable(referenceRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<ReferenceResponse> getReferencesByVisitId(Integer id) {
        return referenceRepository.findAllByMedicalVisit_Id(id)
                .stream()
                .map(Reference::getResponseDto)
                .collect(Collectors.toList());
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
        def.setMedicalVisit(medicalVisitRepository.findById(referenceRequest.getVisit().getId()).orElse(null));
        def.setDetails(referenceRequest.getDetails());
        def.setReferenceTo(referenceRequest.getReferenceTo());
        if(referenceRequest.getCode() == null) def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        return referenceRepository.save(def);
    }

    public List<ReferenceResponse> getVisitHistoryOfSubject(String subjectFromRequest) {
        return referenceRepository.findAllByMedicalVisit_Patient_Subject(subjectFromRequest)
                .stream()
                .map(Reference::getResponseDto)
                .collect(Collectors.toList());
    }
}
