package com.clovis.okr.repository;

import com.clovis.okr.model.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyResultRepository extends JpaRepository<KeyResult, Integer> {
  List<KeyResult> findByObjectiveId(Integer objectiveId);
}
