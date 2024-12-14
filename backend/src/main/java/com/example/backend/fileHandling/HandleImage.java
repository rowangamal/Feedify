package com.example.backend.fileHandling;

import jakarta.transaction.Synchronization;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class HandleImage {
    private static HandleImage handleImage;
    private HandleImage() {
    }
    public static synchronized HandleImage  getInstance() {
        if (handleImage == null) {
            handleImage = new HandleImage();
        }
        return handleImage;
    }
//    ../frontend/public/uploads/post/
    public synchronized String saveImage(MultipartFile image , String parentPath) throws IOException {
        String fileName = null;
        String storedPath = null;
        if (image != null && !image.isEmpty()) {
            Path uploadPath = Paths.get(parentPath);
            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);
            String path = image.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
            fileName = getFileName(path)+"_"+ timestamp + getFileExtension(path);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath , StandardCopyOption.REPLACE_EXISTING);
            storedPath = fileName;
        }
        return storedPath;
    }
    public synchronized void deleteImage(String path) throws IOException {
        Path filePath = Paths.get(path);
        Files.delete(filePath);
    }

    private synchronized String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    private synchronized String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
