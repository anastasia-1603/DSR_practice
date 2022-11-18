package com.example.practice.utils;

import java.io.*;
import java.nio.file.*;

import com.example.practice.exceptions.CreateDirectoriesException;
import com.example.practice.exceptions.DeleteFileException;
import com.example.practice.exceptions.DirectoryNotFoundException;
import com.example.practice.exceptions.ImageNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new CreateDirectoriesException("Could not create directory " + uploadDir);
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageNotFoundException("Could not save image file: " + fileName);
        }
    }

    public static void deleteFile(String fileDirectory) {
        Path filePath = Paths.get(fileDirectory);
        try {
            Files.delete(filePath);
        } catch (NoSuchFileException x) {
            throw new ImageNotFoundException("File not found");
        } catch (IOException x) {
            throw new DeleteFileException("Could not delete file");
        }
    }
}