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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks/edit")
public class TaskController {

    protected TaskRepository taskRepository;
    protected final TaskItemRepository taskItemRepository;

    public TaskController(TaskRepository taskRepository, TaskItemRepository taskItemRepository) {
        this.taskRepository = taskRepository;
        this.taskItemRepository = taskItemRepository;
    }

    @GetMapping
    public String index(@RequestParam("id") String taskId, Model model) {
        Long numTaskId = Long.valueOf(taskId);
        Task task = taskRepository.findById(numTaskId).orElse(null);

        if (task == null) {
            return "redirect:/tasks";
        }

        UserDTO taskOwner = task.getOwner();
        String ownerUsername = taskOwner.getUsername();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        if (!currentUsername.equals(ownerUsername)) {
            return "redirect:/tasks";
        }

        List<TaskItem> taskItems = taskItemRepository.findAll();
        List<TaskItem> taskItemsForTask = getTaskItemsForTask(task, taskItems);

        model.addAttribute("taskItems", taskItemsForTask);
        model.addAttribute("task", task);
        model.addAttribute("owner", taskOwner);
        model.addAttribute("user", taskOwner);

        return "openedTask";
    }

    public List<TaskItem> getTaskItemsForTask(Task task, List<TaskItem> tasks) {
        Long targetTaskId = task.getId();
        return tasks.stream()
                .filter(taskItem -> taskItem.getTaskId().equals(targetTaskId))
                .collect(Collectors.toList());
    }
}
