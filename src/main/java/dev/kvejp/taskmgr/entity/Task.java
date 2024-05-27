package dev.kvejp.taskmgr.entity;

import jakarta.persistence.*;
import dev.kvejp.taskmgr.utils.UnixTimestampConverter;

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

    @Column(name = "DATE_CREATED", nullable = false)
    protected String dateCreated;

    @Column(name = "COMPLETED", nullable = false)
    protected boolean completed;

    public Task(){}

    public Task(UserDTO owner, String taskName) {
        UnixTimestampConverter timestampConverter = new UnixTimestampConverter();
        int timestampCreated = (int) (System.currentTimeMillis() / 1000);

        this.owner = owner;
        this.taskName = taskName;
        this.dateCreated = timestampConverter.convert(timestampCreated, "UTC");
        this.completed = false;
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
    public String getDateCreated() {
        return dateCreated;
    }
    public boolean isCompleted() {
        return completed;
    }
}
