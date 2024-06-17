package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.TaskItem;
import dev.kvejp.taskmgr.repository.TaskItemRepository;
import dev.kvejp.taskmgr.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task/addItem")
public class TaskAddItemController {

    protected final TaskItemRepository taskItemRepository;
    protected final TaskRepository taskRepository;
    public TaskAddItemController(TaskItemRepository taskItemRepository, TaskRepository taskRepository) {
        this.taskItemRepository = taskItemRepository;
        this.taskRepository = taskRepository;
    }
    @PostMapping
    public ResponseEntity<Long> addItem(@RequestParam("taskId") Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.badRequest().build();
        }

        String taskOwner = task.getOwner().getUsername();

        if (!username.equals(taskOwner)) {
            return ResponseEntity.badRequest().build();
        }

        Long id = Long.valueOf(taskId + String.valueOf(System.currentTimeMillis()));
        TaskItem taskItem = new TaskItem(id, taskId);

        taskItemRepository.save(taskItem);

        return ResponseEntity.ok(id);
    }
}

