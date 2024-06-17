package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.TaskItem;
import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.TaskItemRepository;
import dev.kvejp.taskmgr.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller()
@RequestMapping("/tasks/")
public class TaskUpdateController {

    protected TaskRepository taskRepository;
    protected TaskItemRepository taskItemRepository;
    public TaskUpdateController(TaskRepository taskRepository, TaskItemRepository taskItemRepository) {
        this.taskRepository = taskRepository;
        this.taskItemRepository = taskItemRepository;
    }
    @PostMapping("/update")
    public String update(@RequestParam("taskName") String taskName, @RequestParam("id") String taskId, @RequestParam("completed") boolean completed, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Long numTaskId = Long.valueOf(taskId);
        Task task = taskRepository.findById(numTaskId).orElse(null);

        if (task == null) {
            return "redirect:/tasks";
        }

        UserDTO taskOwner = task.getOwner();
        String ownerUsername = taskOwner.getUsername();

        if (!currentUsername.equals(ownerUsername)) {
            return "redirect:/tasks";
        }

        task.setName(taskName);
        task.setCompleted(completed);
        taskRepository.save(task);

        return "redirect:/tasks/edit?id=" + taskId;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") String taskId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Long numTaskId = Long.valueOf(taskId);
        Task task = taskRepository.findById(numTaskId).orElse(null);

        if (task == null) {
            return "redirect:/tasks";
        }

        UserDTO taskOwner = task.getOwner();
        String ownerUsername = taskOwner.getUsername();

        if (!currentUsername.equals(ownerUsername)) {
            return "redirect:/tasks";
        }

        taskRepository.delete(task);

        return "redirect:/tasks";
    }

    @PostMapping("/moveItem")
    public ResponseEntity<Void> move(@RequestParam("id") String taskId, @RequestParam("taskItemId") Long itemId, @RequestParam("taskItemTargetColumn") int targetColumn, @RequestParam("taskItemTextContent") String textContent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Long numTaskId = Long.valueOf(taskId);
        Task task = taskRepository.findById(numTaskId).orElse(null);
        TaskItem taskItem = taskItemRepository.findById(itemId).orElse(null);

        if (taskItem == null || task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String taskOwnerUsername = task.getOwner().getUsername();

        if (!currentUsername.equals(taskOwnerUsername)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        taskItem.setColumn(targetColumn);
        taskItem.setTextContent(textContent);

        taskItemRepository.save(taskItem);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
