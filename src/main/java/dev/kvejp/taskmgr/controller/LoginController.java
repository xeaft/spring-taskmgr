package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.UserRepository;
import dev.kvejp.taskmgr.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    protected final UserRepository userRepository;
    protected final AuthenticationService authenticationService;

    public LoginController(UserRepository userService, AuthenticationService authenticationService) {
        this.userRepository = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String login() {
        boolean isAuthenticated = authenticationService.isAuthenticated();
        if (isAuthenticated) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping
    public String loginPost(@RequestParam("username") String name, @RequestParam("password") String password) {
        // TODO: password hashing, just about everywhere

        List<UserDTO> users = userRepository.findAll();

        if (users.stream().anyMatch(u -> u.getUsername().equals(name) && u.getPassword().equals(password))) {
            return "redirect:/";
        }

        return "login";
    }
}
