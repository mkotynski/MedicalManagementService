package com.mkotynski.mmf.Receipt.ReceiptPosition;

import com.mkotynski.mmf.Recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ReceiptPositionRepository extends JpaRepository<ReceiptPosition, Integer> {
    @Transactional
    @Modifying
    void deleteAllByReceipt_Id(Integer receiptId);

    @Transactional
    @Modifying
    @Query("delete from ReceiptPosition m where m not in :positions and m.receipt.id = :receiptId")
    void deleteWhereNotExists(Set<ReceiptPosition> positions, Integer receiptId);

    @Query("select m from ReceiptPosition m where m not in :positions and m.receipt.id = :receiptId")
    List<ReceiptPosition> selectWhereNotExists(List<ReceiptPosition> positions, Integer receiptId);

    List<ReceiptPosition> findAllByReceipt_Id(Integer receiptId);
}
