package com.clovis.okr.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InitiativeCreateDTO {
  @NotBlank
  private String title;

  private String description;

  @DecimalMin("0.0")
  @DecimalMax("100.0")
  private Double completionPercentage = 0.0;

  @NotNull
  private Integer keyResultId;

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

  public Integer getKeyResultId() {
    return keyResultId;
  }

  public void setKeyResultId(Integer keyResultId) {
    this.keyResultId = keyResultId;
  }
}
