package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.repository.DoctorRepository;
import com.mkotynski.mmf.repository.SpecializationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorService {

    DoctorRepository doctorRepository;
    SpecializationTypeRepository specializationTypeRepository;

    public Optional<DoctorResponse> getDoctor(Doctor doctor) {
        return Optional.ofNullable(doctor.getResponseDto());
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getResponseDto)
                .collect(Collectors.toList());
    }

    public Doctor saveDoctor(@RequestBody DoctorRequest doctorRequest) {
        Doctor def = new Doctor();
        if (doctorRequest.getId() != null) {
            Optional<Doctor> object = doctorRepository.findById(doctorRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setSurname(doctorRequest.getSurname());
        def.setName(doctorRequest.getName());
        def.setDateOfEmployment(doctorRequest.getDateOfEmployment());
        def.setSpecializationType(specializationTypeRepository.findById(doctorRequest.getSpecializationType()).get());

        return doctorRepository.save(def);
    }

}
