package com.clovis.okr.controller;

import com.clovis.okr.dto.ObjectiveCreateDTO;
import com.clovis.okr.dto.ObjectiveDTO;
import com.clovis.okr.service.ObjectiveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectives")
public class ObjectiveController {

  private final ObjectiveService objectiveService;

  @Autowired
  public ObjectiveController(ObjectiveService objectiveService) {
    this.objectiveService = objectiveService;
  }

  @GetMapping
  public ResponseEntity<List<ObjectiveDTO>> getAllObjectives() {
    return ResponseEntity.ok(objectiveService.getAllObjectives());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ObjectiveDTO> getObjectiveById(@PathVariable Integer id) {
    return ResponseEntity.ok(objectiveService.getObjectiveById(id));
  }

  @PostMapping
  public ResponseEntity<ObjectiveDTO> createObjective(@Valid @RequestBody ObjectiveCreateDTO createDTO) {
    return new ResponseEntity<>(objectiveService.createObjective(createDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObjectiveDTO> updateObjective(@PathVariable Integer id, @Valid @RequestBody ObjectiveCreateDTO updateDTO) {
    return ResponseEntity.ok(objectiveService.updateObjective(id, updateDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteObjective(@PathVariable Integer id) {
    objectiveService.deleteObjective(id);
    return ResponseEntity.noContent().build();
  }
}
