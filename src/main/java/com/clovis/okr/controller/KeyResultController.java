package com.clovis.okr.controller;

import com.clovis.okr.dto.KeyResultCreateDTO;
import com.clovis.okr.dto.KeyResultDTO;
import com.clovis.okr.service.KeyResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-results")
public class KeyResultController {

  private final KeyResultService keyResultService;

  @Autowired
  public KeyResultController(KeyResultService keyResultService) {
    this.keyResultService = keyResultService;
  }

  @GetMapping
  public ResponseEntity<List<KeyResultDTO>> getAllKeyResults() {
    return ResponseEntity.ok(keyResultService.getAllKeyResults());
  }

  @GetMapping("/{id}")
  public ResponseEntity<KeyResultDTO> getKeyResultById(@PathVariable Integer id) {
    return ResponseEntity.ok(keyResultService.getKeyResultById(id));
  }

  @GetMapping("/objective/{objectiveId}")
  public ResponseEntity<List<KeyResultDTO>> getKeyResultsByObjectiveId(@PathVariable Integer objectiveId) {
    return ResponseEntity.ok(keyResultService.getKeyResultsByObjectiveId(objectiveId));
  }

  @PostMapping
  public ResponseEntity<KeyResultDTO> createKeyResult(@Valid @RequestBody KeyResultCreateDTO createDTO) {
    return new ResponseEntity<>(keyResultService.createKeyResult(createDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<KeyResultDTO> updateKeyResult(@PathVariable Integer id, @Valid @RequestBody KeyResultCreateDTO updateDTO) {
    return ResponseEntity.ok(keyResultService.updateKeyResult(id, updateDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteKeyResult(@PathVariable Integer id) {
    keyResultService.deleteKeyResult(id);
    return ResponseEntity.noContent().build();
  }
}
