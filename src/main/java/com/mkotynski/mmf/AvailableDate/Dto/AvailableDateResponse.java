package com.mkotynski.mmf.AvailableDate.Dto;

import com.mkotynski.mmf.Doctor.Dto.DoctorResponse;
import com.mkotynski.mmf.Enums.RepeatablePeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableDateResponse {
    private Integer id;
    private Date date;
    private Date endDate;
    private Integer duration;
    private Boolean repeatable;
    private Boolean reserved;
    private RepeatablePeriod repeatablePeriod;
    private DoctorResponse doctor;
}
