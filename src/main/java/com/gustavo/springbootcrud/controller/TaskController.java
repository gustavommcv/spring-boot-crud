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

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId)
            throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        return ResponseEntity.ok().body(task);
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) { // valid
        return taskRepository.save(task);
    }

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

    @DeleteMapping("tasks/{id}")
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
