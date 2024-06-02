package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.UserRepository;
import dev.kvejp.taskmgr.utils.UserDataValidator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {
    protected UserRepository userRepository;
    protected UserDataValidator userDataValidationService;
    protected String[] usersColors = {"#4169E1", "#228B22", "#DC143C", "#FFD700", "#FF7F50", "#7B68EE", "#FF8C00", "#008080", "#708090", "#FF1493", "#BDB76B", "#20B2AA", "#B22222", "#4682B4", "#C71585", "#F4A460", "#9932CC", "#1E90FF", "#3CB371", "#FF6347"};

    public RegisterController(UserRepository userService, UserDataValidator userDataValidationService) {
        this.userRepository = userService;
        this.userDataValidationService = userDataValidationService;
    }
    @GetMapping
    public String register(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = !(auth instanceof AnonymousAuthenticationToken);

        if (isAuthenticated) {
            return "redirect:/";
        }

        return "register";
    }

    @PostMapping
    public String register(@RequestParam("username") String name, @RequestParam("password") String password, @RequestParam("repeatpassword") String repeatpassword) {
        if (!password.equals(repeatpassword)) {
            return "redirect:/register?error=passwordsDontMatch";
        }

        String passwordError = userDataValidationService.isValid(name, password);
        if (!passwordError.isEmpty()) {
            return "redirect:/register?error=" + passwordError;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String profileColor = usersColors[new Random().nextInt(usersColors.length)];

        UserDTO newUser = new UserDTO(name, hashedPassword, profileColor);
        userRepository.save(newUser);

        return "redirect:/login";
    }
}
