package com.mkotynski.mmf.Recipe.Dto;

import com.mkotynski.mmf.Doctor.Dto.DoctorResponse;
import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitResponse;
import com.mkotynski.mmf.Patient.Dto.PatientResponse;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionRequest;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionResponse;
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
    private MedicalVisitResponse visit;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private Date date;
    private Date expirationDate;
    private Set<RecipePositionRequest> positions;
}
