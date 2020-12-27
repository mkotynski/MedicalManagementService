package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.ReceiptPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptPositionRepository extends JpaRepository<ReceiptPosition, Integer> {
}
