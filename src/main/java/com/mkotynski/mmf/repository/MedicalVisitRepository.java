package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.MedicalVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Integer> {
}
