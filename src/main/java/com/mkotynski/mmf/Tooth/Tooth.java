package com.mkotynski.mmf.Tooth;


import com.mkotynski.mmf.Patient.Patient;
import com.mkotynski.mmf.Tooth.Dto.ToothResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "m_tooth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tooth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "top_side")
    private Boolean topSide;

    @Column(name = "left_side")
    private Boolean leftSide;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public ToothResponse getResponseDto() {
        return ToothResponse.builder()
                .id(this.id)
                .number(this.number)
                .top(this.topSide)
                .left(this.leftSide)
                .patient(this.patient.getResponseDto())
                .build();
    }
}


