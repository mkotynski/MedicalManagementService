package com.mkotynski.mmf.Patient;

import com.mkotynski.mmf.Patient.Dto.PatientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "m_patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "dateOfRegister")
    private Date dateOfRegister;

    @Column(name = "subject")
    private String subject;

    public PatientResponse getResponseDto() {
        return PatientResponse.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .dateOfRegister(this.dateOfRegister)
                .build();
    }
}

