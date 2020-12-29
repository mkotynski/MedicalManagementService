package com.mkotynski.mmf.Recipe.RecipePosition;

import com.mkotynski.mmf.Recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface RecipePositionRepository extends JpaRepository<RecipePosition, Integer> {
    @Transactional
    @Modifying
    void deleteAllByRecipe_Id(Integer recipeId);

    @Transactional
    @Modifying
    @Query("delete from RecipePosition m where m not in :positions and m.recipe.id = :recipeId")
    void deleteWhereNotExists(Set<RecipePosition> positions, Integer recipeId);

    @Transactional
    @Modifying
    @Query("delete from RecipePosition m where m.recipe.id in :positions")
    void deleteWhereIdExistsInPositions(List<Integer> positions);

}
