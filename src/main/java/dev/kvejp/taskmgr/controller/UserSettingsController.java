package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import dev.kvejp.taskmgr.entity.UserDTO;

import java.util.Optional;

@Controller
public class UserSettingsController {
    protected final UserRepository userRepository;

    public UserSettingsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Optional<UserDTO> OptionalUser = userRepository.findByUsername(name);

        if (OptionalUser.isPresent()) {
            UserDTO user = OptionalUser.get();
            model.addAttribute("user", user);
        }

        return "index";
    }

    @PostMapping
    public String changeSetting(@RequestParam("setting") String setting, @RequestParam("value") String value) {
        return "index";
    }
}
