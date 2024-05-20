package dev.kvejp.taskmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserSettingsController {
    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping
    public String changeSetting(@RequestParam("setting") String setting, @RequestParam("value") String value) {
        System.out.println("Setting changed: " + setting + " to " + value);
        return "index";
    }
}
