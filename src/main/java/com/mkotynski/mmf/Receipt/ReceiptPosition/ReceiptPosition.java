package com.mkotynski.mmf.Receipt.ReceiptPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.Receipt.Receipt;
import com.mkotynski.mmf.Receipt.ReceiptPosition.Dto.ReceiptPositionResponse;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String description;

    @Column(name = "value")
    private Double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    @JsonIgnoreProperties("receiptPositionSet")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Receipt receipt;

    public ReceiptPositionResponse getResponseDto(){
        return ReceiptPositionResponse.builder()
                .id(this.id)
                .description(this.description)
                .receipt(this.receipt.getResponseDtoWithoutReceiptPositions())
                .value(this.value)
                .build();
    }

}
