package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.entity.Patient;
import com.mkotynski.mmf.enums.RepeatablePeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRequest {
    private Integer id;
    private String code;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private Date date;
    private Date expirationDate;
    private Set<RecipePositionResponse> positions;
}
