package com.example.backend.FileHandling;

import com.example.backend.fileHandling.HandleImage;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ImageHandleTest {
    @Test
    void deleteImageWhichIsNotFound() throws IOException {

        String path = "rafy/hany/said.jpg";
        HandleImage handleImage = HandleImage.getInstance();

        handleImage.deleteImage(path);
    }

    @Test
    void getPathOfImageThatIsNotInsertedTest() throws IOException {

        HandleImage handleImage = HandleImage.getInstance();

        String path = handleImage.saveImage(null, "rafy/hany/said.jpg");

        assertNull( path);
    }

    @Test
    void getPathOfImageThatIsInsertedTest() throws IOException {

        HandleImage handleImage = HandleImage.getInstance();
        MultipartFile image = new MockMultipartFile("image", "rafy.jpg", "image/jpg", "rafy.jpg".getBytes());

        String path = handleImage.saveImage(image, "../frontend/public/uploads/post/");

        assertNotNull(path);
    }
}
