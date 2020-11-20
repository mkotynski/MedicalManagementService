package com.mkotynski.mmf.dto.mapper;

import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.entity.Doctor;

public class DoctorMapper {
    public static DoctorResponse getResponseDtoFromEntity(Doctor entity) {
        if (entity == null) {
            return null;
        }
        return DoctorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .dateOfEmployment(entity.getDateOfEmployment())
                .specializationType(entity.getSpecializationType())
                .build();
    }

}
