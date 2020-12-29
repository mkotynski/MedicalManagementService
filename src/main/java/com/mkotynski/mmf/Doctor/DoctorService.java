package com.mkotynski.mmf.Doctor;


import com.mkotynski.mmf.Doctor.Dto.DoctorRequest;
import com.mkotynski.mmf.Doctor.Dto.DoctorResponse;
import com.mkotynski.mmf.SpecializationType.SpecializationTypeRepository;
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

    public Optional<DoctorResponse> getDoctor(Integer id) {
        return Optional.ofNullable(doctorRepository.findById(id).orElse(null).getResponseDto());
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
        def.setSpecializationType(specializationTypeRepository.findById(doctorRequest.getSpecializationType().getId()).get());

        return doctorRepository.save(def);
    }

}
