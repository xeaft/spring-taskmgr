package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/tasks/")
public class TaskUpdateController {
    protected TaskRepository taskRepository;

    public TaskUpdateController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
}
