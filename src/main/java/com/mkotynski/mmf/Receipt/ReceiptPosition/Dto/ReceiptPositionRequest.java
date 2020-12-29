package com.mkotynski.mmf.Receipt.ReceiptPosition.Dto;

import com.mkotynski.mmf.Receipt.Dto.ReceiptResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptPositionRequest {
    private Integer id;
    private String description;
    private ReceiptResponse receipt;
    private Double value;

}
