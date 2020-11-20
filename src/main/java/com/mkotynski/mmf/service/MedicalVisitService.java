package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.dto.MedicalVisitRequest;
import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.dto.mapper.DoctorMapper;
import com.mkotynski.mmf.dto.mapper.MedicalVisitMapper;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.MedicalVisit;
import com.mkotynski.mmf.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalVisitService {

    MedicalVisitRepository medicalVisitRepository;
    VisitTypeRepository visitTypeRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public Optional<MedicalVisitResponse> getMedicalVisit(MedicalVisit medicalVisit) {
        return Optional.ofNullable(MedicalVisitMapper.getResponseDtoFromEntity(medicalVisit));
    }

    public List<MedicalVisitResponse> getAllMedicalVisit() {
        return medicalVisitRepository.findAll()
                .stream()
                .map(MedicalVisitMapper::getResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public MedicalVisit saveMedicalVisit(@RequestBody MedicalVisitRequest medicalVisitRequest) {
        MedicalVisit def = new MedicalVisit();
        if (medicalVisitRequest.getId() != null) {
            Optional<MedicalVisit> object = medicalVisitRepository.findById(medicalVisitRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setName(medicalVisitRequest.getName());
        def.setDate(medicalVisitRequest.getDate());
        def.setTime(medicalVisitRequest.getTime());
        def.setVisitType(visitTypeRepository.getOne(medicalVisitRequest.getVisitType()));
        def.setDescription(medicalVisitRequest.getDescription());
        def.setDoctor(doctorRepository.getOne(medicalVisitRequest.getDoctor()));
        def.setPatient(patientRepository.getOne(medicalVisitRequest.getPatient()));

        return medicalVisitRepository.save(def);
    }

}
