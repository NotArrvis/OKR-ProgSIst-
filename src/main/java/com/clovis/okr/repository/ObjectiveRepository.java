package com.clovis.okr.repository;

import com.clovis.okr.model.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveRepository extends JpaRepository<Objective, Integer> {
}
