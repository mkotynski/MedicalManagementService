package com.mkotynski.mmf.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.Doctor.Doctor;
import com.mkotynski.mmf.MedicalVisit.MedicalVisit;
import com.mkotynski.mmf.Patient.Patient;
import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePosition;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="m_recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;

    @OneToOne
    @JoinColumn(name = "visit_id")
    private MedicalVisit medicalVisit;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date")
    private Date date;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("recipe")
    private Set<RecipePosition> recipePositionSet;

    public RecipeResponse getResponseDto(){
        return RecipeResponse.builder()
                .id(this.id)
                .code(this.code)
                .visit(this.medicalVisit.getResponseDto())
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .positions( this.recipePositionSet != null ? this.recipePositionSet.stream().map(RecipePosition::getResponseDto).collect(Collectors.toSet()) : null
                )
                .build();
    }

    public RecipeResponse getResponseDtoWithoutRecipePositions(){
        return RecipeResponse.builder()
                .id(this.id)
                .code(this.code)
                .visit(this.medicalVisit.getResponseDto())
                .doctor(this.doctor.getResponseDto())
                .patient(this.patient.getResponseDto())
                .date(this.date)
                .expirationDate(this.expirationDate)
                .build();
    }
}
