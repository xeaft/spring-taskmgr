package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tasks/edit")
public class TaskController {

    protected TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String index(@RequestParam("id") String taskId, Model model) {
        Long numTaskId = Long.valueOf(taskId);
        Task task = taskRepository.findById(numTaskId).orElse(null);

        if (task == null) {
            return "tasks";
        }

        UserDTO taskOwner = task.getOwner();
        String ownerUsername = taskOwner.getUsername();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        if (!currentUsername.equals(ownerUsername)) {
            return "tasks";
        }

       model.addAttribute("task", task);
       model.addAttribute("owner", taskOwner);

        return "openedTask";
    }
}
