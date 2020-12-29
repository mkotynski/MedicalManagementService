package com.mkotynski.mmf.Doctor.Dto;

import com.mkotynski.mmf.SpecializationType.Dto.SpecializationTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private Integer id;
    private String name;
    private String surname;
    private Date dateOfEmployment;
    private SpecializationTypeResponse specializationType;
}
