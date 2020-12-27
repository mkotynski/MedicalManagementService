package com.mkotynski.mmf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.dto.ReceiptResponse;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.enums.ReceiptStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="m_receipt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date")
    private Date date;

    @Column(name = "receipt_status")
    private ReceiptStatus receiptStatus;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("receipt")
    private Set<ReceiptPosition> receiptPositionSet;

    public ReceiptResponse getResponseDto(){
        return ReceiptResponse.builder()
                .id(this.id)
                .code(this.code)
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .positions( this.receiptPositionSet != null ? this.receiptPositionSet.stream().map(ReceiptPosition::getResponseDto).collect(Collectors.toSet()) : null
                )
                .build();
    }

    public ReceiptResponse getResponseDtoWithoutReceiptPositions(){
        return ReceiptResponse.builder()
                .id(this.id)
                .code(this.code)
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .build();
    }
}
