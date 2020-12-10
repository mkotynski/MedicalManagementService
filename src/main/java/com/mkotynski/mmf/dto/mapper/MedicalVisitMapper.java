package com.mkotynski.mmf.dto.mapper;

import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.MedicalVisit;

public class MedicalVisitMapper {
    public static MedicalVisitResponse getResponseDtoFromEntity(MedicalVisit entity) {
        if (entity == null) {
            return null;
        }
        return MedicalVisitResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .description(entity.getDescription())
                .visitType(entity.getVisitType().getResponseDto())
                .doctor(entity.getDoctor().getResponseDto())
                .patient(entity.getPatient().getResponseDto())
                .build();
    }

}
