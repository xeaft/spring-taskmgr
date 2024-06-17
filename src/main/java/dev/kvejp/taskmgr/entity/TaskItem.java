package dev.kvejp.taskmgr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK_ITEM")
public class TaskItem {
    public TaskItem(){
        this.id = -1L;
        this.taskId = -1L;
    };

    @Id
    protected final Long id;
    @Column(name = "TEXT_CONTENT")
    private String textContent;
    @Column(name = "COLUMN_NUMBER")
    private int column;
    @Column(name = "TASK_ID")
    protected final Long taskId;
    public TaskItem(Long id, Long taskId) {
        this.textContent = "";
        this.column = 0;
        this.id = id;
        this.taskId = taskId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }
}
