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
        //given
        String path = "rafy/hany/said.jpg";
        HandleImage handleImage = HandleImage.getInstance();
        //when
        handleImage.deleteImage(path);
    }

    @Test
    void getPathOfImageThatIsNotInsertedTest() throws IOException {
        //given
        HandleImage handleImage = HandleImage.getInstance();
        //when
        String path = handleImage.saveImage(null, "rafy/hany/said.jpg");
        //then
        assertNull( path);
    }

    @Test
    void getPathOfImageThatIsInsertedTest() throws IOException {
        //given
        HandleImage handleImage = HandleImage.getInstance();
        MultipartFile image = new MockMultipartFile("image", "rafy.jpg", "image/jpg", "rafy.jpg".getBytes());

        //when
        String path = handleImage.saveImage(image, "../frontend/public/uploads/post/");

        //then
        assertNotNull(path);
    }
}
