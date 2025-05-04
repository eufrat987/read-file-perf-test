package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @GetMapping("/read")
    public int read() throws IOException {
        return Files.readAllBytes(Path.of("myfile.bin")).length;
    }

    @GetMapping("/readBuf")
    public int readBuf() throws IOException {
        return readFileBytesStream("myfile.bin");
    }

    @GetMapping("/log")
    public String log() {
        return Thread.currentThread().toString();
    }

    private int readFileBytesStream(String path) throws IOException {
        File file = new File(path);
        byte[] buffer = new byte[8192];
        int totalBytes = 0;
        int bytesRead;

        try (FileInputStream inputStream = new FileInputStream(file)) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }
        }

        return totalBytes;
    }
}