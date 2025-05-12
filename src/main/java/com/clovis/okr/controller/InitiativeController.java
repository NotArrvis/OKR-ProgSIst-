package com.clovis.okr.controller;

import com.clovis.okr.dto.InitiativeCreateDTO;
import com.clovis.okr.dto.InitiativeDTO;
import com.clovis.okr.service.InitiativeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/initiatives")
public class InitiativeController {

  private final InitiativeService initiativeService;

  @Autowired
  public InitiativeController(InitiativeService initiativeService) {
    this.initiativeService = initiativeService;
  }

  @GetMapping
  public ResponseEntity<List<InitiativeDTO>> getAllInitiatives() {
    return ResponseEntity.ok(initiativeService.getAllInitiatives());
  }

  @GetMapping("/{id}")
  public ResponseEntity<InitiativeDTO> getInitiativeById(@PathVariable Integer id) {
    return ResponseEntity.ok(initiativeService.getInitiativeById(id));
  }

  @GetMapping("/key-result/{keyResultId}")
  public ResponseEntity<List<InitiativeDTO>> getInitiativesByKeyResultId(@PathVariable Integer keyResultId) {
    return ResponseEntity.ok(initiativeService.getInitiativesByKeyResultId(keyResultId));
  }

  @PostMapping
  public ResponseEntity<InitiativeDTO> createInitiative(@Valid @RequestBody InitiativeCreateDTO createDTO) {
    return new ResponseEntity<>(initiativeService.createInitiative(createDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InitiativeDTO> updateInitiative(@PathVariable Integer id, @Valid @RequestBody InitiativeCreateDTO updateDTO) {
    return ResponseEntity.ok(initiativeService.updateInitiative(id, updateDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInitiative(@PathVariable Integer id) {
    initiativeService.deleteInitiative(id);
    return ResponseEntity.noContent().build();
  }
}
