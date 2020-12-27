package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.VisitTypeRequest;
import com.mkotynski.mmf.dto.VisitTypeResponse;
import com.mkotynski.mmf.entity.VisitType;
import com.mkotynski.mmf.repository.VisitTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitTypeService {

    VisitTypeRepository visitTypeRepository;

    public Optional<VisitTypeResponse> getVisitType(VisitType visitType) {
        return Optional.ofNullable(visitType.getResponseDto());
    }

    public Optional<VisitTypeResponse> getVisitType(Integer id) {
        return Optional.ofNullable(visitTypeRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<VisitTypeResponse> getAllVisitTypes() {
        return visitTypeRepository.findAll()
                .stream()
                .map(VisitType::getResponseDto)
                .collect(Collectors.toList());
    }

    public VisitType saveVisitType(@RequestBody VisitTypeRequest visitTypeRequest) {
        VisitType def = new VisitType();
        if (visitTypeRequest.getId() != null) {
            Optional<VisitType> object = visitTypeRepository.findById(visitTypeRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setName(visitTypeRequest.getName());
        def.setDescription(visitTypeRequest.getDescription());

        return visitTypeRepository.save(def);
    }
}
