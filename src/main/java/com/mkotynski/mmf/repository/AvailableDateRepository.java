package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.AvailableDate;
import com.mkotynski.mmf.entity.Doctor;
import com.mkotynski.mmf.entity.MedicalVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Integer> {
    List<AvailableDate> findAllByDoctor_Id(Integer id);
}
