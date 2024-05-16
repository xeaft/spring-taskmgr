package dev.kvejp.taskmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AnonUserPageController {
    @GetMapping("/features")
    public String features() {
        return "features";
    }

    @GetMapping("/pricing") // not gonna be in the final version
    public String pricing() {
        return "pricing";
    }
}
