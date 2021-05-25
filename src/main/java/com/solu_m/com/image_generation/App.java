package com.solu_m.com.image_generation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@Slf4j
@RestController
@RequestMapping("api/v1")
public class App {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
    }

    @PostMapping(value = "/generate_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPngImage(@RequestParam(required = true) MultipartFile file,
                                              @RequestParam(required = true) MultipartFile imageData) {
        try {
            String xslData = new String(file.getBytes(), StandardCharsets.UTF_8);
            String xmlData = new String(imageData.getBytes(), StandardCharsets.UTF_8);
            ImageCreation imageCreation = new ImageCreation();
            byte[] bytes = imageCreation.convertXMLandXSLToImage(xmlData, xslData);
            return ResponseEntity.ok().body(bytes);
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X_EXCEPTION_CAUSE", e.getMessage())
                    .build();
        }
    }


    @GetMapping(value = "/getallfonts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllFonts() throws FontFormatException, IOException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/usr/share/fonts/exception.ttf")));
        return ResponseEntity.ok().body("Exception thrown");
    }

}
