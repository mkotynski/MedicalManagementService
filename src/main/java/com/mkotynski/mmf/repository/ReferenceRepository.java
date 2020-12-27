package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
}
