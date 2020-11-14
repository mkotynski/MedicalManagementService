package com.mkotynski.mmf.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="m_medical_visit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

    @ManyToOne
    private VisitType visitType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;
}
