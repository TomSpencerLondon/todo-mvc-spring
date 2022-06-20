package com.example.todomvcthymeleaf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class TodoItem {

  @Id
  @GeneratedValue
  private Long id;

  public TodoItem(String title, boolean isComplete) {
    this.title = title;
    this.completed = isComplete;
  }

  public TodoItem() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @NotBlank
  private String title;

  private boolean completed;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
