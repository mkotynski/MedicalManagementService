package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.SpecializationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationTypeRepository extends JpaRepository<SpecializationType, Integer> {
}
