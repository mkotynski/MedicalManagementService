package com.mkotynski.mmf.Tooth.ToothHistory;


import com.mkotynski.mmf.Tooth.Tooth;
import com.mkotynski.mmf.Tooth.ToothHistory.Dto.ToothHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "m_tooth_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToothHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "tooth_id")
    private Tooth tooth;

    @Column(name = "description")
    private String description;

    public ToothHistoryResponse getResponseDto() {
        return ToothHistoryResponse.builder()
                .id(this.id)
                .date(this.date)
                .tooth(this.tooth.getResponseDto())
                .description(this.description)
                .build();
    }
}


