package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitTypeRepository extends JpaRepository<VisitType, Integer> {
}
