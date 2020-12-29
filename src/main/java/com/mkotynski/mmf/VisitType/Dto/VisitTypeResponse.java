package com.mkotynski.mmf.VisitType.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitTypeResponse {
    private Integer id;
    private String name;
    private String description;
}
