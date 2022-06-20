package com.example.todomvcthymeleaf;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
  int countAllByCompleted(boolean completed);

  List<TodoItem> findAllByCompleted(boolean completed);
}
