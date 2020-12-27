package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.MedicalVisitRequest;
import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.entity.MedicalVisit;
import com.mkotynski.mmf.repository.DoctorRepository;
import com.mkotynski.mmf.repository.MedicalVisitRepository;
import com.mkotynski.mmf.repository.PatientRepository;
import com.mkotynski.mmf.repository.VisitTypeRepository;
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
        return Optional.ofNullable(medicalVisit.getResponseDto());
    }

    public Optional<MedicalVisitResponse> getMedicalVisit(Integer id) {
        return Optional.ofNullable(medicalVisitRepository.findById(id).orElse(null).getResponseDto());
    }

    public Optional<MedicalVisitResponse> getMedicalVisit(Integer id, String subject) {
        return Optional.ofNullable(medicalVisitRepository.findByIdAndDoctor_SubjectOrPatient_Subject(id,subject, subject).getResponseDto());
    }

    public List<MedicalVisitResponse> getAllMedicalVisit() {
        return medicalVisitRepository.findAll()
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }
    public List<MedicalVisitResponse> getAllMedicalVisit(String subject) {
        return medicalVisitRepository.findAllByDoctor_SubjectOrPatient_Subject(subject, subject)
                .stream()
                .map(MedicalVisit::getResponseDto)
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
        def.setEndDate(medicalVisitRequest.getEndDate());
        def.setDone(medicalVisitRequest.getDone());
        def.setVisitType(visitTypeRepository.findById(medicalVisitRequest.getVisitType().getId()).get());
        def.setDescription(medicalVisitRequest.getDescription());
        def.setDoctor(doctorRepository.findById(medicalVisitRequest.getDoctor().getId()).get());
        def.setPatient(patientRepository.findById(medicalVisitRequest.getPatient().getId()).get());

        return medicalVisitRepository.save(def);
    }

    public List<MedicalVisitResponse> getAllMedicalVisitByPatientId(Integer id) {
        return medicalVisitRepository.findAllByPatient_Id(id)
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }

    public List<MedicalVisitResponse> getAllMedicalVisitByPatientId(String subject) {
        return getAllMedicalVisitByPatientId(doctorRepository.findBySubject(subject).getId());
    }

}
