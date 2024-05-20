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
    protected UserDTO owner;

    @Column(name = "TASK_NAME", nullable = false)
    protected String taskName;

    public Task(){}

    public Task(UserDTO owner, String taskName) {
        this.owner = owner;
        this.taskName = taskName;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public String getName() {
        return taskName;
    }
}
