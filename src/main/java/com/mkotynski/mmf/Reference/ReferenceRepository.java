package com.mkotynski.mmf.Reference;

import com.mkotynski.mmf.Recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {

    List<Reference> findAllByMedicalVisit_Id(Integer id);

    @Transactional
    @Modifying
    void deleteAllByMedicalVisit_Id(Integer medicalVisitId);

    @Transactional
    @Modifying
    @Query("delete from Reference m where m not in :positions and m.medicalVisit.id = :medicalVisitId")
    void deleteWhereNotExists(List<Reference> positions, Integer medicalVisitId);

    @Query("select m from Reference m where m not in :positions and m.medicalVisit.id = :medicalVisitId")
    List<Reference> selectWhereNotExists(List<Reference> positions, Integer medicalVisitId);

    List<Reference> findAllByMedicalVisit_Patient_Subject(String subject);

}
