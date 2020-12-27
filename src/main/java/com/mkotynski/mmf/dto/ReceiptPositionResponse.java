package com.mkotynski.mmf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptPositionResponse {
    private Integer id;
    private String description;
    private ReceiptResponse receipt;
    private Double value;
}
