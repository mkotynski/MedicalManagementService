package com.mkotynski.mmf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceResponse {
    private Integer id;
    private String code;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private Date date;
    private Date expirationDate;
    private String referenceTo;
    private String details;}
