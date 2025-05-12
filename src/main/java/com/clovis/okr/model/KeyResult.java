package com.clovis.okr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "key_results")
public class KeyResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  private String description;

  @NotBlank
  private String goal;

  @DecimalMin("0.0")
  @DecimalMax("100.0")
  @Column(name = "completion_percentage", columnDefinition = "NUMERIC(5,2)")
  private Double completionPercentage = 0.0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "objective_id")
  private Objective objective;

  @OneToMany(mappedBy = "keyResult", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Initiative> initiatives = new ArrayList<>();

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

  public Objective getObjective() {
    return objective;
  }

  public void setObjective(Objective objective) {
    this.objective = objective;
  }

  public List<Initiative> getInitiatives() {
    return initiatives;
  }

  public void setInitiatives(List<Initiative> initiatives) {
    this.initiatives = initiatives;
  }

  public void addInitiative(Initiative initiative) {
    initiatives.add(initiative);
    initiative.setKeyResult(this);
  }

  public void removeInitiative(Initiative initiative) {
    initiatives.remove(initiative);
    initiative.setKeyResult(null);
  }
}
