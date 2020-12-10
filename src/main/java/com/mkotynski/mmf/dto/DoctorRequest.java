package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.SpecializationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {
    private Integer id;
    private String name;
    private String surname;
    private Date dateOfEmployment;
    private Integer specializationType;

    public Doctor getEntity(SpecializationType specializationType){
        Doctor doctor = new Doctor();

        doctor.setName(this.name);
        doctor.setSurname(this.surname);
        doctor.setDateOfEmployment(this.dateOfEmployment);
        doctor.setSpecializationType(specializationType);

        return doctor;
    }
}
