package com.clovis.okr.dto;

public class InitiativeDTO {
  private Integer id;
  private String title;
  private String description;
  private Double completionPercentage;
  private Integer keyResultId;

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

  public Integer getKeyResultId() {
    return keyResultId;
  }

  public void setKeyResultId(Integer keyResultId) {
    this.keyResultId = keyResultId;
  }
}
