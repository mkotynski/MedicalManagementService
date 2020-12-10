package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.AvailableDate;
import com.mkotynski.mmf.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Integer> {
}
