package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.entity.SpecializationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {
    private Integer id;
    private String name;
    private String surname;
    private Date dateOfRegister;
}
