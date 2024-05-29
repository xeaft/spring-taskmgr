package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public LoginController(UserRepository userService) {
        this.userRepository = userService;
    }

    @GetMapping
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = !(auth instanceof AnonymousAuthenticationToken);

        if (isAuthenticated) {
            return "redirect:/";
        }

        return "login";
    }

    @PostMapping
    public String loginPost(@RequestParam("username") String name, @RequestParam("password") String password) {
        List<UserDTO> users = userRepository.findAll();

        if (users.stream().anyMatch(u -> u.getUsername().equals(name) && u.getPassword().equals(password))) {
            return "redirect:/";
        }

        return "login";
    }
}
