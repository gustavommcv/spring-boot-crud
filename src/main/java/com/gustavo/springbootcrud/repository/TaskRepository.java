package com.gustavo.springbootcrud.repository;

import com.gustavo.springbootcrud.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TaskRepository extends JpaRepository<Task, Long> { }
