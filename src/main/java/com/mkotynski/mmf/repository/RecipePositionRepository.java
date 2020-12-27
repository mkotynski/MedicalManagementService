package com.mkotynski.mmf.repository;

import com.mkotynski.mmf.entity.RecipePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipePositionRepository extends JpaRepository<RecipePosition, Integer> {
}
