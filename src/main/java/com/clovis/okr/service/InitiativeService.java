package com.clovis.okr.service;

import com.clovis.okr.dto.InitiativeCreateDTO;
import com.clovis.okr.dto.InitiativeDTO;
import com.clovis.okr.model.Initiative;
import com.clovis.okr.model.KeyResult;
import com.clovis.okr.repository.InitiativeRepository;
import com.clovis.okr.repository.KeyResultRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final KeyResultRepository keyResultRepository;
  private final CompletionServiceHelper completionServiceHelper;

  @Autowired
  public InitiativeService(InitiativeRepository initiativeRepository,
                           KeyResultRepository keyResultRepository,
                           CompletionServiceHelper completionServiceHelper) {
    this.initiativeRepository = initiativeRepository;
    this.keyResultRepository = keyResultRepository;
    this.completionServiceHelper = completionServiceHelper;
  }

  public List<InitiativeDTO> getAllInitiatives() {
    return initiativeRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public List<InitiativeDTO> getInitiativesByKeyResultId(Integer keyResultId) {
    return initiativeRepository.findByKeyResultId(keyResultId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public InitiativeDTO getInitiativeById(Integer id) {
    Initiative initiative = initiativeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Initiative not found with id: " + id));
    return convertToDTO(initiative);
  }

  @Transactional
  public InitiativeDTO createInitiative(InitiativeCreateDTO createDTO) {
    KeyResult keyResult = keyResultRepository.findById(createDTO.getKeyResultId())
        .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + createDTO.getKeyResultId()));

    Initiative initiative = new Initiative();
    initiative.setTitle(createDTO.getTitle());
    initiative.setDescription(createDTO.getDescription());
    initiative.setCompletionPercentage(createDTO.getCompletionPercentage() != null ? createDTO.getCompletionPercentage() : 0.0);
    initiative.setKeyResult(keyResult);

    initiative = initiativeRepository.save(initiative);

    completionServiceHelper.updateKeyResultCompletion(keyResult.getId());

    return convertToDTO(initiative);
  }

  @Transactional
  public InitiativeDTO updateInitiative(Integer id, InitiativeCreateDTO updateDTO) {
    Initiative initiative = initiativeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Initiative not found with id: " + id));

    Integer oldKeyResultId = initiative.getKeyResult().getId();

    initiative.setTitle(updateDTO.getTitle());
    initiative.setDescription(updateDTO.getDescription());
    initiative.setCompletionPercentage(updateDTO.getCompletionPercentage());

    if (!initiative.getKeyResult().getId().equals(updateDTO.getKeyResultId())) {
      KeyResult newKeyResult = keyResultRepository.findById(updateDTO.getKeyResultId())
          .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + updateDTO.getKeyResultId()));
      initiative.setKeyResult(newKeyResult);
    }

    initiative = initiativeRepository.save(initiative);

    completionServiceHelper.updateKeyResultCompletion(initiative.getKeyResult().getId());
    if (!oldKeyResultId.equals(initiative.getKeyResult().getId())) {
      completionServiceHelper.updateKeyResultCompletion(oldKeyResultId);
    }

    return convertToDTO(initiative);
  }

  @Transactional
  public void deleteInitiative(Integer id) {
    Initiative initiative = initiativeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Initiative not found with id: " + id));

    Integer keyResultId = initiative.getKeyResult().getId();
    initiativeRepository.deleteById(id);

    completionServiceHelper.updateKeyResultCompletion(keyResultId);
  }

  public InitiativeDTO convertToDTO(Initiative initiative) {
    InitiativeDTO dto = new InitiativeDTO();
    dto.setId(initiative.getId());
    dto.setTitle(initiative.getTitle());
    dto.setDescription(initiative.getDescription());
    dto.setCompletionPercentage(initiative.getCompletionPercentage());

    if (initiative.getKeyResult() != null) {
      dto.setKeyResultId(initiative.getKeyResult().getId());
    }

    return dto;
  }
}
