package dev.kvejp.taskmgr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    protected User owner;

    @Column(name = "TASK_NAME", unique = true, nullable = false)
    protected String taskName;

    public Task(){}

    public Task(User owner) {
        this.owner = owner;
        // TODO: number the untitled tasks for name duplication
        this.taskName = "Untitled";
    }

    public Task(User owner, String taskName) {
        this.owner = owner;
        this.taskName = taskName;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return taskName;
    }
}
