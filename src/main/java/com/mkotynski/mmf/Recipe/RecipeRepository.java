package com.mkotynski.mmf.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByMedicalVisit_Id(Integer id);

    @Transactional
    @Modifying
    void deleteAllByMedicalVisit_Id(Integer medicalVisitId);

    @Transactional
    @Modifying
    @Query("delete from Recipe m where m not in :positions and m.medicalVisit.id = :medicalVisitId")
    void deleteWhereNotExists(List<Recipe> positions, Integer medicalVisitId);

    @Query("select m from Recipe m where m not in :positions and m.medicalVisit.id = :medicalVisitId")
    List<Recipe> selectWhereNotExists(List<Recipe> positions, Integer medicalVisitId);

}
