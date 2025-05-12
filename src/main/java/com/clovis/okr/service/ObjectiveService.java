package com.clovis.okr.service;

import com.clovis.okr.dto.ObjectiveCreateDTO;
import com.clovis.okr.dto.ObjectiveDTO;
import com.clovis.okr.model.Objective;
import com.clovis.okr.repository.ObjectiveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectiveService {

  private final ObjectiveRepository objectiveRepository;
  private final KeyResultService keyResultService;

  @Autowired
  public ObjectiveService(ObjectiveRepository objectiveRepository, KeyResultService keyResultService) {
    this.objectiveRepository = objectiveRepository;
    this.keyResultService = keyResultService;
  }

  public List<ObjectiveDTO> getAllObjectives() {
    return objectiveRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ObjectiveDTO getObjectiveById(Integer id) {
    Objective objective = objectiveRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + id));
    return convertToDTO(objective);
  }

  @Transactional
  public ObjectiveDTO createObjective(ObjectiveCreateDTO createDTO) {
    Objective objective = new Objective();
    objective.setTitle(createDTO.getTitle());
    objective.setDescription(createDTO.getDescription());
    objective.setCompletionPercentage(0.0);
    
    objective = objectiveRepository.save(objective);
    return convertToDTO(objective);
  }

  @Transactional
  public ObjectiveDTO updateObjective(Integer id, ObjectiveCreateDTO updateDTO) {
    Objective objective = objectiveRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + id));

    objective.setTitle(updateDTO.getTitle());
    objective.setDescription(updateDTO.getDescription());

    objective = objectiveRepository.save(objective);
    return convertToDTO(objective);
  }

  @Transactional
  public void deleteObjective(Integer id) {
    if (!objectiveRepository.existsById(id)) {
      throw new EntityNotFoundException("Objective not found with id: " + id);
    }
    objectiveRepository.deleteById(id);
  }

  public ObjectiveDTO convertToDTO(Objective objective) {
    ObjectiveDTO dto = new ObjectiveDTO();
    dto.setId(objective.getId());
    dto.setTitle(objective.getTitle());
    dto.setDescription(objective.getDescription());
    dto.setCompletionPercentage(objective.getCompletionPercentage());

    if (objective.getKeyResults() != null) {
      dto.setKeyResults(objective.getKeyResults().stream()
          .map(keyResultService::convertToDTO)
          .collect(Collectors.toList()));
    }

    return dto;
  }
}
