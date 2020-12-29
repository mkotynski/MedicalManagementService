package com.mkotynski.mmf.Reference.Dto;

import com.mkotynski.mmf.Doctor.Dto.DoctorResponse;
import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitResponse;
import com.mkotynski.mmf.MedicalVisit.MedicalVisit;
import com.mkotynski.mmf.Patient.Dto.PatientResponse;
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
    private MedicalVisitResponse visit;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private Date date;
    private Date expirationDate;
    private String referenceTo;
    private String details;}
