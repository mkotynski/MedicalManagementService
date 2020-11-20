package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.Patient;
import com.mkotynski.mmf.entity.SpecializationType;
import com.mkotynski.mmf.entity.VisitType;
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
    private Time time;
    private VisitType visitType;
    private String description;
    private Doctor doctor;
    private Patient patient;
}
