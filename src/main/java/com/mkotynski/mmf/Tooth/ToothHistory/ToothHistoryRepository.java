package com.mkotynski.mmf.Tooth.ToothHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToothHistoryRepository extends JpaRepository<ToothHistory, Integer> {

    List<ToothHistory> findAllByTooth_Id(Integer toothId);
}
