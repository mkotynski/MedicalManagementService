package com.mkotynski.mmf.entity;


import com.mkotynski.mmf.dto.SpecializationTypeResponse;
import com.mkotynski.mmf.dto.ToothResponse;
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

    @Column(name = "top")
    private Boolean top;

    @Column(name = "left")
    private Boolean left;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public ToothResponse getResponseDto() {
        return ToothResponse.builder()
                .id(this.id)
                .number(this.number)
                .top(this.top)
                .left(this.left)
                .patient(this.patient.getResponseDto())
                .build();
    }
}


