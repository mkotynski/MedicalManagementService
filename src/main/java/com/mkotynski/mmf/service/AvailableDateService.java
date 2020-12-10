package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.AvailableDateRequest;
import com.mkotynski.mmf.dto.AvailableDateResponse;
import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.entity.AvailableDate;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.repository.AvailableDateRepository;
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
public class AvailableDateService {

    AvailableDateRepository availableDateRepository;
    DoctorRepository doctorRepository;

    public Optional<AvailableDateResponse> getAvailableDate(AvailableDate availableDate) {
        return Optional.ofNullable(availableDate.getResponseDto());
    }

    public List<AvailableDateResponse> getAllAvailableDates() {
        return availableDateRepository.findAll()
                .stream()
                .map(AvailableDate::getResponseDto)
                .collect(Collectors.toList());
    }

    public AvailableDate saveAvailableDate(@RequestBody AvailableDateRequest availableDateRequest) {
        AvailableDate def = new AvailableDate();
        if (availableDateRequest.getId() != null) {
            Optional<AvailableDate> object = availableDateRepository.findById(availableDateRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDate(availableDateRequest.getDate());
        def.setDoctor(doctorRepository.findById(availableDateRequest.getDoctor()).get());
        def.setRepeatable(availableDateRequest.getRepeatable());
        def.setRepeatablePeriod(availableDateRequest.getRepeatablePeriod());

        return availableDateRepository.save(def);
    }
}
