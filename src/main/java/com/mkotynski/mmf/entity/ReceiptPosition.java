package com.mkotynski.mmf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.dto.ReceiptPositionResponse;
import com.mkotynski.mmf.dto.ReceiptResponse;
import com.mkotynski.mmf.dto.RecipePositionResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="m_receipt_position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String desription;

    @Column(name = "value")
    private Double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    @JsonIgnoreProperties("receiptPositionSet")
    private Receipt receipt;

    public ReceiptPositionResponse getResponseDto(){
        return ReceiptPositionResponse.builder()
                .id(this.id)
                .description(this.desription)
                .receipt(this.receipt.getResponseDtoWithoutReceiptPositions())
                .value(this.value)
                .build();
    }

}
