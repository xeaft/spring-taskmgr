package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Task;
import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.TaskRepository;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // TODO: add JS preventDefault call and len validation (keep this as well though)
        if (task == null || task.isEmpty() || task.length() > 50) {
            return "/tasks/create";
        }

        List<Task> tasks = taskRepository.findAll();
        String taskName = task;

        // TODO: again, add clientside JS validation
        for (Task task1 : tasks) {
            if (task1.getName().equals(taskName)) {
                return "/tasks/create";
            }
        }


        // TODO: login stuff...
        List<UserDTO> users = userRepository.findAll();
        UserDTO user = users.get(0);

        if (user == null) {
            return "/register";
        }

        Task newTask = new Task(user, task);
        taskRepository.save(newTask);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        return "redirect:/tasks";
    }

    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/create")
    public String taskCreatePage() {
        return "taskCreate";
    }
}
