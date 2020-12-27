package com.mkotynski.mmf.entity;

import com.mkotynski.mmf.dto.ReferenceResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "m_reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Patient patient;

    @Column(name = "date")
    private Date date;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "reference_to")
    private String referenceTo;

    @Column(name = "details")
    private String details;

    public ReferenceResponse getResponseDto() {
        return ReferenceResponse.builder()
                .id(this.id)
                .code(this.code)
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .referenceTo(this.referenceTo)
                .details(this.details)
                .build();
    }

}
