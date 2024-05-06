package dev.kvejp.taskmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tasks/{taskId}")
public class TaskController {
    @GetMapping
    public String index(@RequestParam("taskId") Long taskId) {
        // TODO: implement task fetching

        return "tasks";
    }
}
