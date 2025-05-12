package com.clovis.okr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class KeyResultCreateDTO {
  @NotBlank
  private String description;

  @NotBlank
  private String goal;

  @NotNull
  private Integer objectiveId;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGoal() {
    return goal;
  }

  public void setGoal(String goal) {
    this.goal = goal;
  }

  public Integer getObjectiveId() {
    return objectiveId;
  }

  public void setObjectiveId(Integer objectiveId) {
    this.objectiveId = objectiveId;
  }
}
