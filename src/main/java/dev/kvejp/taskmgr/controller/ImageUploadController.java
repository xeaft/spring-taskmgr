package dev.kvejp.taskmgr.controller;

import dev.kvejp.taskmgr.entity.UserDTO;
import dev.kvejp.taskmgr.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@Controller
public class ImageUploadController {
    protected final UserRepository userRepository;

    public ImageUploadController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public String uploadProfilePic(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return "redirect:/settings?error=nofile";
        }

        try {
            String filename = getFileName(Objects.requireNonNull(file.getOriginalFilename()));
            String[] fileNameSplit = filename.split("\\.");
            String fileExtension = fileNameSplit[fileNameSplit.length - 1];

            String baseDir = System.getProperty("user.dir");
            String fileName = generateRandomFileName() + "." + fileExtension;
            String filePath = baseDir + File.separator + getFilePath(fileName);

            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            File dest = new File(filePath);
            file.transferTo(dest);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            Optional<UserDTO> OptionalUser = userRepository.findByUsername(name);

            if (OptionalUser.isPresent()) {
                UserDTO user = OptionalUser.get();

                if (user.getHasProfilePic()) {
                    String oldFileName = user.getProfilePic();
                    File oldFile = new File(baseDir + getFilePath(oldFileName));
                    oldFile.delete();
                }

                user.setProfilePic(fileName);
                user.setHasProfilePic(true);
                userRepository.save(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/settings?error=uploaderror";
        }

        return "redirect:/settings";
    }

    private static String generateRandomFileName() {
        return UUID.randomUUID().toString();
    }

    private static String getFileName(String fileName) {
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        return fileName.substring(startIndex + 1);
    }

    private static String getFilePath(String fileName) {
        return "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "uploads" + File.separator +
                fileName;
    }
}