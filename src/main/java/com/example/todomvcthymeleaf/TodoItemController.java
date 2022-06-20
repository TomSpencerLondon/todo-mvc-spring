package com.example.todomvcthymeleaf;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoItemController {

  private final TodoItemRepository repository;

  public TodoItemController(TodoItemRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public String index(Model model) {
    addAttributesForIndex(model, ListFilter.ALL);
    return "index";
  }

  private void addAttributesForIndex(Model model,
      ListFilter listFilter) {
    model.addAttribute("item", new TodoItemFormData());
    model.addAttribute("filter", listFilter);
    model.addAttribute("todos", getTodoItems(listFilter));
    model.addAttribute("totalNumberOfItems", repository.count());
    model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
    model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems());
  }

  @PostMapping
  public String addNewTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
    repository.save(new TodoItem(formData.getTitle(), false));

    return "redirect:/";
  }

  @PutMapping("/{id}/toggle")
  public String toggleSelection(@PathVariable("id") Long id) {
    TodoItem todoItem = repository
        .findById(id)
        .orElseThrow(() -> new TodoItemNotFoundException(id));

    todoItem.setCompleted(!todoItem.isCompleted());
    repository.save(todoItem);

    return "redirect:/";
  }

  private List<TodoItemDto> getTodoItems(ListFilter filter) {
    return switch (filter) {
      case ALL -> convertToDto(repository.findAll());
      case ACTIVE -> convertToDto(repository.findAllByCompleted(false));
      case COMPLETED -> convertToDto(repository.findAllByCompleted(true));
    };
  }

  private List<TodoItemDto> convertToDto(List<TodoItem> todoItems) {
    return todoItems
        .stream()
        .map(todoItem -> new TodoItemDto(todoItem.getId(),
            todoItem.getTitle(),
            todoItem.isCompleted()))
        .collect(Collectors.toList());
  }

  private int getNumberOfActiveItems() {
    return repository.countAllByCompleted(false);
  }

  private int getNumberOfCompletedItems() {
    return repository.countAllByCompleted(true);
  }

  public static record TodoItemDto(long id, String title, boolean completed) {
  }

  public enum ListFilter {
    ALL,
    ACTIVE,
    COMPLETED
  }
}
