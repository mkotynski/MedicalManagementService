package com.mkotynski.mmf.Recipe.RecipePosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkotynski.mmf.Recipe.Recipe;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionResponse;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="m_recipe_position")
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
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    @JsonIgnoreProperties("recipePositionSet")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;

    public RecipePositionResponse getResponseDto(){
        return RecipePositionResponse.builder()
                .id(this.id)
                .description(this.description)
                .recipe(this.recipe.getResponseDtoWithoutRecipePositions())
                .build();
    }

}
