package com.mkotynski.mmf.entity;

import com.mkotynski.mmf.dto.SpecializationTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "m_specialization_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    public SpecializationTypeResponse getResponseDto() {
        return SpecializationTypeResponse.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}


