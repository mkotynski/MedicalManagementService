package com.mkotynski.mmf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.enums.VisitStatus;
import lombok.*;

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
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "time")
    private Time time;

    @ManyToOne
    private VisitType visitType;

    @Column(name = "description")
    private String description;

    @Column(name = "visit_status")
    private VisitStatus visitStatus;

    @ManyToOne
    @JsonIgnoreProperties("specializationType")
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    public MedicalVisitResponse getResponseDto(){
        return MedicalVisitResponse.builder()
                .id(this.id)
                .name(this.name)
                .date(this.date)
                .endDate(this.endDate)
                .time(this.time)
                .done(this.done)
                .visitType(this.visitType.getResponseDto())
                .visitStatus(this.visitStatus)
                .description(this.description)
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .build();
    }
}
