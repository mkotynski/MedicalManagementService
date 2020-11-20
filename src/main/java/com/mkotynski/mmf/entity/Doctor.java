package com.mkotynski.mmf.entity;

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

}
