package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.dto.MedicalVisitResponse;
import com.mkotynski.mmf.entity.MedicalVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Integer> {

    List<MedicalVisit> findAllByPatient_Id(Integer id);
}
