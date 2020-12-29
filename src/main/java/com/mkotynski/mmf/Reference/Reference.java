package com.mkotynski.mmf.Reference;

import com.mkotynski.mmf.Doctor.Doctor;
import com.mkotynski.mmf.MedicalVisit.MedicalVisit;
import com.mkotynski.mmf.Patient.Patient;
import com.mkotynski.mmf.Reference.Dto.ReferenceResponse;
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

    @OneToOne
    @JoinColumn(name = "visit_id")
    private MedicalVisit medicalVisit;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "patient_id")
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
                .visit(this.medicalVisit.getResponseDto())
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .referenceTo(this.referenceTo)
                .details(this.details)
                .build();
    }

}
