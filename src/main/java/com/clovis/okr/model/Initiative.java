package com.clovis.okr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "initiatives")
public class Initiative {

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "key_result_id")
  private KeyResult keyResult;

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

  public KeyResult getKeyResult() {
    return keyResult;
  }

  public void setKeyResult(KeyResult keyResult) {
    this.keyResult = keyResult;
  }
}
