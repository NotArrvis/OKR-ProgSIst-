package com.clovis.okr.service;

import com.clovis.okr.dto.KeyResultCreateDTO;
import com.clovis.okr.dto.KeyResultDTO;
import com.clovis.okr.model.KeyResult;
import com.clovis.okr.model.Objective;
import com.clovis.okr.repository.KeyResultRepository;
import com.clovis.okr.repository.ObjectiveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyResultService {

  private final KeyResultRepository keyResultRepository;
  private final ObjectiveRepository objectiveRepository;
  private final InitiativeService initiativeService;
  private final CompletionServiceHelper completionServiceHelper;

  @Autowired
  public KeyResultService(KeyResultRepository keyResultRepository,
                          ObjectiveRepository objectiveRepository,
                          InitiativeService initiativeService,
                          CompletionServiceHelper completionServiceHelper) {
    this.keyResultRepository = keyResultRepository;
    this.objectiveRepository = objectiveRepository;
    this.initiativeService = initiativeService;
    this.completionServiceHelper = completionServiceHelper;
  }

  public List<KeyResultDTO> getAllKeyResults() {
    return keyResultRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public List<KeyResultDTO> getKeyResultsByObjectiveId(Integer objectiveId) {
    return keyResultRepository.findByObjectiveId(objectiveId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public KeyResultDTO getKeyResultById(Integer id) {
    KeyResult keyResult = keyResultRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + id));
    return convertToDTO(keyResult);
  }

  @Transactional
  public KeyResultDTO createKeyResult(KeyResultCreateDTO createDTO) {
    Objective objective = objectiveRepository.findById(createDTO.getObjectiveId())
        .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + createDTO.getObjectiveId()));

    KeyResult keyResult = new KeyResult();
    keyResult.setDescription(createDTO.getDescription());
    keyResult.setGoal(createDTO.getGoal());
    keyResult.setCompletionPercentage(0.0);
    keyResult.setObjective(objective);

    keyResult = keyResultRepository.save(keyResult);

    completionServiceHelper.updateObjectiveCompletion(objective.getId());

    return convertToDTO(keyResult);
  }

  @Transactional
  public KeyResultDTO updateKeyResult(Integer id, KeyResultCreateDTO updateDTO) {
    KeyResult keyResult = keyResultRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + id));

    Integer oldObjectiveId = keyResult.getObjective().getId();

    keyResult.setDescription(updateDTO.getDescription());
    keyResult.setGoal(updateDTO.getGoal());

    if (!keyResult.getObjective().getId().equals(updateDTO.getObjectiveId())) {
      Objective newObjective = objectiveRepository.findById(updateDTO.getObjectiveId())
          .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + updateDTO.getObjectiveId()));
      keyResult.setObjective(newObjective);
    }

    keyResult = keyResultRepository.save(keyResult);

    completionServiceHelper.updateObjectiveCompletion(keyResult.getObjective().getId());
    if (!oldObjectiveId.equals(keyResult.getObjective().getId())) {
      completionServiceHelper.updateObjectiveCompletion(oldObjectiveId);
    }

    return convertToDTO(keyResult);
  }

  @Transactional
  public void deleteKeyResult(Integer id) {
    KeyResult keyResult = keyResultRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + id));

    Integer objectiveId = keyResult.getObjective().getId();
    keyResultRepository.deleteById(id);

    completionServiceHelper.updateObjectiveCompletion(objectiveId);
  }

  public KeyResultDTO convertToDTO(KeyResult keyResult) {
    KeyResultDTO dto = new KeyResultDTO();
    dto.setId(keyResult.getId());
    dto.setDescription(keyResult.getDescription());
    dto.setGoal(keyResult.getGoal());
    dto.setCompletionPercentage(keyResult.getCompletionPercentage());

    if (keyResult.getObjective() != null) {
      dto.setObjectiveId(keyResult.getObjective().getId());
    }

    if (keyResult.getInitiatives() != null) {
      dto.setInitiatives(keyResult.getInitiatives().stream()
          .map(initiativeService::convertToDTO)
          .collect(Collectors.toList()));
    }

    return dto;
  }
}
