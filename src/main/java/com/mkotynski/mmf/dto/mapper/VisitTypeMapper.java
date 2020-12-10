package com.mkotynski.mmf.dto.mapper;

import com.mkotynski.mmf.dto.PatientResponse;
import com.mkotynski.mmf.dto.VisitTypeResponse;
import com.mkotynski.mmf.entity.Patient;
import com.mkotynski.mmf.entity.VisitType;

public class VisitTypeMapper {
    public static VisitTypeResponse getResponseDtoFromEntity(VisitType entity) {
        if (entity == null) {
            return null;
        }
        return VisitTypeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

}
