package dev.kvejp.taskmgr.utils;

import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDataValidator {
    protected UserRepository userRepository;

    public UserDataValidator(UserRepository userService) {
        this.userRepository = userService;
    }
    public String isValid(String username, String password) {
        String pattern = "[a-zA-Z0-9]+";
        String numbers = ".*\\d+.*";

        boolean doesPasswordContainNumbers = password.matches(numbers);
        boolean passwordValidCharacters = password.matches(pattern);

        boolean usernameValidCharacters = username.matches(pattern);
        boolean isUsernameLongEnough = username.length() >= 3 && username.length() <= 16;
        boolean isUsernameAvailable = userRepository.findByUsername(username).isEmpty();

        if (!usernameValidCharacters) {
            return "nameInvalid";
        }

        if (!isUsernameLongEnough) {
            return "nameTooShort";
        }

        if (!isUsernameAvailable) {
            return "nameTaken";
        }

        if (password.length() < 8) {
            return "passwordTooShort";
        }

        if (password.length() > 64) {
            return "passwordTooLong";
        }

        if (!doesPasswordContainNumbers) {
            return "passwordNumberReq";
        }

        if (!passwordValidCharacters) {
            return "passwordInvalid";
        }

        return "";
    }
}
