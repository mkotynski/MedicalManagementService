package com.mkotynski.mmf.SpecializationType;


import com.mkotynski.mmf.SpecializationType.Dto.SpecializationTypeRequest;
import com.mkotynski.mmf.SpecializationType.Dto.SpecializationTypeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
