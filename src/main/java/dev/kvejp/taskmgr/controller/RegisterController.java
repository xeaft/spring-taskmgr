package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.User;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserRepository userRepository;

    public RegisterController(UserRepository userService) {
        this.userRepository = userService;
    }
    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam("username") String name, @RequestParam("password") String password) {
        // TODO: password security and tell people if their name is taken (JS)

        List<User> users = userRepository.findAll();
        if (users.stream().anyMatch(u -> u.getUsername().equals(name))) {
            return "redirect:/register";
        }

        User newUser = new User(name, password);
        userRepository.save(newUser);

        return "redirect:/";
    }
}
