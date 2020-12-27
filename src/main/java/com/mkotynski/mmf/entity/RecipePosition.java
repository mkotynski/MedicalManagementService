package com.mkotynski.mmf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.dto.RecipePositionResponse;
import com.mkotynski.mmf.dto.RecipeResponse;
import lombok.*;

import javax.persistence.*;
import javax.sound.midi.Receiver;
import java.util.Date;

@Entity
@Table(name="m_receipt_position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String desription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    @JsonIgnoreProperties("recipePositionSet")
    private Recipe recipe;

    public RecipePositionResponse getResponseDto(){
        return RecipePositionResponse.builder()
                .id(this.id)
                .description(this.desription)
                .recipe(this.recipe.getResponseDtoWithoutRecipePositions())
                .build();
    }

}
