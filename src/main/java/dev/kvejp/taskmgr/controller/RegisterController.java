package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegisterController {
    protected UserRepository userRepository;

    public RegisterController(UserRepository userService) {
        this.userRepository = userService;
    }
    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam("username") String name, @RequestParam("password") String password) {
        String pattern = "[a-zA-Z0-9]+";
        boolean isUsernameValid = name.matches(pattern);

        if (!isUsernameValid) {
            return "redirect:/register?error=nameInvalid";
        }

        if (name.length() < 3) {
            return "redirect:/register?error=nameTooShort";
        }

        Optional<UserDTO> users = userRepository.findByUsername(name);
        if (users.isPresent()) {
            return "redirect:/register?error=nameTaken";
        }

        if (password.length() < 8) {
            return "redirect:/register?error=passwordTooShort";
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserDTO newUser = new UserDTO(name, hashedPassword);
        userRepository.save(newUser);

        return "redirect:/login";
    }
}
