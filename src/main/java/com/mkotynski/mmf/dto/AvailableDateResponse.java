package com.mkotynski.mmf.dto;

import com.mkotynski.mmf.enums.RepeatablePeriod;
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
