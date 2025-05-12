package com.clovis.okr.service;

import com.clovis.okr.model.Initiative;
import com.clovis.okr.model.KeyResult;
import com.clovis.okr.model.Objective;
import com.clovis.okr.repository.KeyResultRepository;
import com.clovis.okr.repository.ObjectiveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompletionServiceHelper {

  private final ObjectiveRepository objectiveRepository;
  private final KeyResultRepository keyResultRepository;

  @Autowired
  public CompletionServiceHelper(ObjectiveRepository objectiveRepository,
                                 KeyResultRepository keyResultRepository) {
    this.objectiveRepository = objectiveRepository;
    this.keyResultRepository = keyResultRepository;
  }

  @Transactional
  public void updateKeyResultCompletion(Integer keyResultId) {
    KeyResult keyResult = keyResultRepository.findById(keyResultId)
        .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + keyResultId));

    List<Initiative> initiatives = keyResult.getInitiatives();
    if (initiatives.isEmpty()) {
      keyResult.setCompletionPercentage(0.0);
    } else {
      double average = initiatives.stream()
          .mapToDouble(Initiative::getCompletionPercentage)
          .average()
          .orElse(0.0);
      keyResult.setCompletionPercentage(average);
    }

    keyResultRepository.save(keyResult);

    if (keyResult.getObjective() != null) {
      updateObjectiveCompletion(keyResult.getObjective().getId());
    }
  }

  @Transactional
  public void updateObjectiveCompletion(Integer objectiveId) {
    Objective objective = objectiveRepository.findById(objectiveId)
        .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + objectiveId));

    List<KeyResult> keyResults = objective.getKeyResults();
    if (keyResults.isEmpty()) {
      objective.setCompletionPercentage(0.0);
    } else {
      double average = keyResults.stream()
          .mapToDouble(KeyResult::getCompletionPercentage)
          .average()
          .orElse(0.0);
      objective.setCompletionPercentage(average);
    }

    objectiveRepository.save(objective);
  }
}
