package com.clovis.okr.dto;

import java.util.List;

public class KeyResultDTO {
  private Integer id;
  private String description;
  private String goal;
  private Double completionPercentage;
  private Integer objectiveId;
  private List<InitiativeDTO> initiatives;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public Double getCompletionPercentage() {
    return completionPercentage;
  }

  public void setCompletionPercentage(Double completionPercentage) {
    this.completionPercentage = completionPercentage;
  }

  public Integer getObjectiveId() {
    return objectiveId;
  }

  public void setObjectiveId(Integer objectiveId) {
    this.objectiveId = objectiveId;
  }

  public List<InitiativeDTO> getInitiatives() {
    return initiatives;
  }

  public void setInitiatives(List<InitiativeDTO> initiatives) {
    this.initiatives = initiatives;
  }
}
