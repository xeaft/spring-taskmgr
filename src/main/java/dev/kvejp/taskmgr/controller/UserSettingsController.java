package dev.kvejp.taskmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSettingsController {
    @GetMapping
    public String home() {
        return "index";
    }
}
