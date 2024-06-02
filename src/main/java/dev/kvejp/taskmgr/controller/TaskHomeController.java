package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.TaskRepository;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskHomeController {
    protected final TaskRepository taskRepository;
    protected final UserRepository userRepository;

    public TaskHomeController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    @PostMapping("/create")
    public String createTask(@RequestParam String task) {
        if (task == null || task.isEmpty() || task.length() > 50) {
            return "/tasks";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        List<Task> tasks = taskRepository.findAll();
        List<Task> userTasks = filterTasksForUser(name, tasks);

        for (Task task1 : userTasks) {
            if (task1.getName().equals(task)) {
                return "redirect:/tasks?error=taskExists";
            }
        }

        Optional<UserDTO> OptionalUser = userRepository.findByUsername(name);

        if (OptionalUser.isPresent()) {
            UserDTO user = OptionalUser.get();
            Task newTask = new Task(user, task);
            taskRepository.save(newTask);
            return "redirect:/tasks";
        }

        return "redirect:/register";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        return "redirect:/tasks";
    }

    @GetMapping
    public String listTasks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        List<Task> tasks = taskRepository.findAll();
        List<Task> userTasks = filterTasksForUser(name, tasks);
        model.addAttribute("tasks", userTasks);

        Optional<UserDTO> OptionalUser = userRepository.findByUsername(name);

        if (OptionalUser.isPresent()) {
            UserDTO user = OptionalUser.get();
            model.addAttribute("user", user);
        }

        return "tasks";
    }
    public List<Task> filterTasksForUser(String username, List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getOwner().getUsername().equals(username))
                .collect(Collectors.toList());
    }
}
