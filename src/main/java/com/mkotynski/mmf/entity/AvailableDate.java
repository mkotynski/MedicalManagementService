package com.mkotynski.mmf.entity;

import com.mkotynski.mmf.dto.AvailableDateResponse;
import com.mkotynski.mmf.dto.DoctorResponse;
import com.mkotynski.mmf.enums.RepeatablePeriod;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="m_available_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "repeatable")
    private Integer repeatable;

    @Column(name = "repeatable_period")
    private RepeatablePeriod repeatablePeriod;

    @ManyToOne
    private Doctor doctor;

    public AvailableDateResponse getResponseDto(){
        return AvailableDateResponse.builder()
                .id(this.id)
                .date(this.date)
                .repeatable(this.repeatable)
                .doctor(this.doctor.getResponseDto())
                .build();
    }

}
