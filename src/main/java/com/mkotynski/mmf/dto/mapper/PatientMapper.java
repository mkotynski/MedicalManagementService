package com.mkotynski.mmf.dto.mapper;

import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.dto.PatientResponse;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.Patient;

public class PatientMapper {
    public static PatientResponse getResponseDtoFromEntity(Patient entity) {
        if (entity == null) {
            return null;
        }
        return PatientResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .dateOfRegister(entity.getDateOfRegister())
                .build();
    }

}
