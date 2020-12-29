package com.mkotynski.mmf.MedicalVisit.Dto;

import com.mkotynski.mmf.Doctor.Dto.DoctorResponse;
import com.mkotynski.mmf.Patient.Dto.PatientResponse;
import com.mkotynski.mmf.VisitType.Dto.VisitTypeResponse;
import com.mkotynski.mmf.Enums.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalVisitResponse {
    private Integer id;
    private String name;
    private Date date;
    private Date endDate;
    private Boolean done;
    private Time time;
    private VisitTypeResponse visitType;
    private VisitStatus visitStatus;
    private String description;
    private DoctorResponse doctor;
    private PatientResponse patient;
}
