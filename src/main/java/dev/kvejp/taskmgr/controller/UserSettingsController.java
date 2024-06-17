package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.Setting;
import dev.kvejp.taskmgr.repository.SettingRepository;
import dev.kvejp.taskmgr.repository.UserRepository;
import dev.kvejp.taskmgr.utils.FileContentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import dev.kvejp.taskmgr.entity.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/settings")
public class UserSettingsController {
    protected final UserRepository userRepository;
    protected final FileContentReader fileContentReader = new FileContentReader();
    protected final String settingsDir;
    protected final SettingRepository settingRepository;

    public UserSettingsController(@Value("${settings.dir}") String settingsDir, UserRepository userRepository, SettingRepository settingRepository) {
        this.userRepository = userRepository;
        this.settingsDir = settingsDir;
        this.settingRepository = settingRepository;
    }

    @GetMapping
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Optional<UserDTO> OptionalUser = userRepository.findByUsername(name);

        boolean refresh = false;

        if (OptionalUser.isPresent()) {
            UserDTO user = OptionalUser.get();

            List<Setting> userSettings = user.getSettings();
            List<String> userSettingNames = userSettings.stream()
                    .map(Setting::getName)
                    .toList();

            List<String> allSettings = fileContentReader.getLines(settingsDir);
            List<String> allSettingNames = allSettings.stream()
                    .map(s -> s.split(",")[0])
                    .toList();

            List<List<String>> settingsList = compareLists(userSettingNames, allSettingNames);
            List<String> missingSettings = settingsList.get(0);
            List<String> extraSettings = settingsList.get(1);

            for (String missingSetting : missingSettings) {
                String setting = findSetting(missingSetting, allSettings);
                if (setting != null) {
                    settingRepository.save(new Setting(missingSetting, setting.split(",")[1], user));
                }
            }

            for (String extraSetting : extraSettings) {
                Setting setting = user.getSetting(extraSetting);
                if (setting != null) {
                    settingRepository.delete(setting);
                }
            }

            if (extraSettings.size() > 0 || missingSettings.size() > 0) {
                refresh = true;
            }

            model.addAttribute("user", user);
        }
        if (refresh) {
            return "redirect:/settings";
        }

        return "userSettings";
    }

    @PostMapping
    public ResponseEntity<Void> changeSetting(@RequestParam("userId") Long userId, @RequestParam("setting") String setting, @RequestParam("enabled") String enabled) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Optional<UserDTO> optionalUser = userRepository.findByUsername(name);

        if (optionalUser.isPresent()) {
            UserDTO loggedUser = optionalUser.get();
            UserDTO user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (user.getId() != loggedUser.getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Setting settingToChange = user.getSetting(setting);
            if (settingToChange == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            settingToChange.setValue(enabled);
            settingRepository.save(settingToChange);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static List<List<String>> compareLists(List<String> l1, List<String> l2) {
        List<String> l1NotInL2 = new ArrayList<>();
        List<String> l2NotInL1 = new ArrayList<>();

        for (String s : l1) {
            if (!l2.contains(s)) {
                l2NotInL1.add(s);
            }
        }

        for (String s : l2) {
            if (!l1.contains(s)) {
                l1NotInL2.add(s);
            }
        }

        List<List<String>> result = new ArrayList<>();
        result.add(l1NotInL2);
        result.add(l2NotInL1);

        return result;
    }

    public static String findSetting(String settingName, List<String> allSettings) {
        for (String str : allSettings) {
            if (str.startsWith(settingName)) {
                return str;
            }
        }
        return null;
    }
}
