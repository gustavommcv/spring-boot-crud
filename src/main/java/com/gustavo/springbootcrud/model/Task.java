package com.gustavo.springbootcrud.model;

import com.gustavo.springbootcrud.model.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks", schema = "springboot_crud")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Task() { this.status = Status.PENDING; }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}
