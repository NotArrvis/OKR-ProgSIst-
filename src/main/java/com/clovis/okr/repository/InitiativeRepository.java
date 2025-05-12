package com.clovis.okr.repository;

import com.clovis.okr.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InitiativeRepository extends JpaRepository<Initiative, Integer> {
  List<Initiative> findByKeyResultId(Integer keyResultId);
}
