# Spring Boot CRUD Application

This is a simple CRUD (Create, Read, Update, Delete) application built using Spring Boot for server-side tasks. It provides HTTP endpoints to manage tasks.

## Frontend
The frontend of this project is available [here](https://github.com/gustavommcv/spring-boot-crud-js-client-side).

## HTTP Methods

- **GET** `/api/v1/tasks`: Retrieve all tasks.
- **GET** `/api/v1/tasks/{id}`: Retrieve a specific task by its ID.
- **POST** `/api/v1/tasks`: Create a new task.
- **PUT** `/api/v1/tasks/{id}`: Update an existing task.
- **DELETE** `/api/v1/tasks/{id}`: Delete a task by its ID.

## Setting Up the Project

Commit from when I started the project: [commit](https://github.com/gustavommcv/spring-boot-crud/commit/13e67e34b7fc2ba4f8b0462a7638248de3610898).

## Project Structure

Here's an overview of the main components of the Spring Boot application:

```java
package com.gustavo.springbootcrud.controller;

import com.gustavo.springbootcrud.exception.ResourceNotFoundException;
import com.gustavo.springbootcrud.model.Task;
import com.gustavo.springbootcrud.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Retrieve all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Retrieve a task by ID
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId)
            throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        return ResponseEntity.ok().body(task);
    }

    // Create a new task
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    // Update an existing task
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId,
                                           @Valid @RequestBody Task taskDetails ) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));

        task.setTitle(taskDetails.getTitle());
        task.setStatus(taskDetails.getStatus());
        task.setDescription(taskDetails.getDescription());

        final Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    // Delete a task by ID
    @DeleteMapping("/tasks/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId)
            throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));

        taskRepository.delete(task);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
```
