package com.mkotynski.mmf.VisitType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitTypeRepository extends JpaRepository<VisitType, Integer> {
}
