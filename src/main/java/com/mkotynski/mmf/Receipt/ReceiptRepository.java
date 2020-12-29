package com.mkotynski.mmf.Receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    Receipt findByMedicalVisit_Id(Integer id);

    @Transactional
    @Modifying
    void deleteAllByMedicalVisit_Id(Integer medicalVisitId);

    @Transactional
    @Modifying
    @Query("delete from Receipt m where m not in :positions and m.medicalVisit.id = :medicalVisitId")
    void deleteWhereNotExists(List<Receipt> positions, Integer medicalVisitId);

}
