package com.mkotynski.mmf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToothHistoryResponse {
    private Integer id;
    private Date date;
    private String description;
    private ToothResponse tooth;
}
