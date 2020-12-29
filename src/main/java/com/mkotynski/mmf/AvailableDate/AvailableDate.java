package com.mkotynski.mmf.AvailableDate;

import com.mkotynski.mmf.AvailableDate.Dto.AvailableDateResponse;
import com.mkotynski.mmf.Doctor.Doctor;
import com.mkotynski.mmf.Enums.RepeatablePeriod;
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

    //in minutes
    @Column(name = "duration")
    private Integer duration;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "repeatable")
    private Boolean repeatable;

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "repeatable_period")
    private RepeatablePeriod repeatablePeriod;

    @ManyToOne
    private Doctor doctor;

    public AvailableDateResponse getResponseDto(){
        return AvailableDateResponse.builder()
                .id(this.id)
                .date(this.date)
                .endDate(this.endDate)
                .duration(this.duration)
                .repeatable(this.repeatable)
                .repeatablePeriod(this.repeatablePeriod)
                .reserved(this.reserved)
                .doctor(this.doctor.getResponseDto())
                .build();
    }

}
