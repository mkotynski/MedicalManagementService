package com.mkotynski.mmf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToothRequest {
    private Integer id;
    private Integer number;
    private Boolean top;
    private Boolean left;
    private PatientResponse patient;
}
