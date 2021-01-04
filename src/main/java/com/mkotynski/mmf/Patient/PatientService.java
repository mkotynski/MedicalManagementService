package com.mkotynski.mmf.Patient;


import com.mkotynski.mmf.Patient.Dto.PatientRequest;
import com.mkotynski.mmf.Patient.Dto.PatientResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {
    PatientRepository patientRepository;

    public Optional<PatientResponse> getPatient(Patient patient) {
        return Optional.ofNullable(patient.getResponseDto());
    }

    public Optional<PatientResponse> getPatient(Integer id) {
        return Optional.ofNullable(patientRepository.findById(id).orElse(null).getResponseDto());
    }


    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(Patient::getResponseDto)
                .collect(Collectors.toList());
    }

    public Patient savePatient(@RequestBody PatientRequest patientRequest) {
        Patient def = new Patient();
        if (patientRequest.getId() != null) {
            Optional<Patient> object = patientRepository.findById(patientRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setName(patientRequest.getName());
        def.setSurname(patientRequest.getSurname());
        def.setDateOfRegister(patientRequest.getDateOfRegister());

        return patientRepository.save(def);
    }


    public Optional<PatientResponse> getPatientBySubject(String subjectFromRequest) {

        return Optional.ofNullable(patientRepository.findBySubject(subjectFromRequest).getResponseDto());
    }
}
