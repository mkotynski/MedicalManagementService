package com.mkotynski.mmf.entity;

import com.mkotynski.mmf.dto.DoctorRequest;
import com.mkotynski.mmf.dto.DoctorResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="m_doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "dateOfEmployment")
    private Date dateOfEmployment;

    @ManyToOne
    private SpecializationType specializationType;

    public DoctorResponse getResponseDto(){
        return DoctorResponse.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .dateOfEmployment(this.dateOfEmployment)
                .specializationType(this.specializationType.getResponseDto())
                .build();
    }

}
