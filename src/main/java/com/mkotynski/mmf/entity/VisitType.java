package com.mkotynski.mmf.entity;


import com.mkotynski.mmf.dto.VisitTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "m_visit_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public VisitTypeResponse getResponseDto() {
        return VisitTypeResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .build();
    }
}
