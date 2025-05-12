package com.clovis.okr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "objectives")
public class Objective {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  private String title;

  private String description;

  @DecimalMin("0.0")
  @DecimalMax("100.0")
  @Column(name = "completion_percentage", columnDefinition = "NUMERIC(5,2)")
  private Double completionPercentage = 0.0;

  @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<KeyResult> keyResults = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getCompletionPercentage() {
    return completionPercentage;
  }

  public void setCompletionPercentage(Double completionPercentage) {
    this.completionPercentage = completionPercentage;
  }

  public List<KeyResult> getKeyResults() {
    return keyResults;
  }

  public void setKeyResults(List<KeyResult> keyResults) {
    this.keyResults = keyResults;
  }

  public void addKeyResult(KeyResult keyResult) {
    keyResults.add(keyResult);
    keyResult.setObjective(this);
  }

  public void removeKeyResult(KeyResult keyResult) {
    keyResults.remove(keyResult);
    keyResult.setObjective(null);
  }
}
