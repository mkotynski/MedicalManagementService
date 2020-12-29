package com.mkotynski.mmf.Tooth;


import com.mkotynski.mmf.Tooth.Dto.ToothRequest;
import com.mkotynski.mmf.Tooth.Dto.ToothResponse;
import com.mkotynski.mmf.Patient.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToothService {


    ToothRepository toothRepository;
    PatientRepository patientRepository;

    public Optional<ToothResponse> getTooth(Tooth tooth) {
        return Optional.ofNullable(tooth.getResponseDto());
    }

    public Optional<ToothResponse> getTooth(Integer id) {
        return Optional.ofNullable(toothRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<ToothResponse> getAllTooth() {
        return toothRepository.findAll()
                .stream()
                .map(Tooth::getResponseDto)
                .collect(Collectors.toList());
    }

    public Tooth saveTooth(@RequestBody ToothRequest toothRequest) {
        Tooth def = new Tooth();
        if (toothRequest.getId() != null) {
            Optional<Tooth> object = toothRepository.findById(toothRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setTopSide(toothRequest.getLeft());
        def.setLeftSide(toothRequest.getTop());
        def.setNumber(toothRequest.getNumber());
        def.setPatient(patientRepository.findById(toothRequest.getPatient().getId()).orElse(null));

        return toothRepository.save(def);
    }

}
