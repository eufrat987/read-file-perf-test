package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class IOController {
    public static final String FILE_PATH = "myfile.bin";

    @GetMapping("/virtual")
    public int virtual() throws IOException {
//        System.out.println(Thread.currentThread().isVirtual());
        return readFileBlocking();
    }

    private int readFileBlocking() throws IOException {
        var bytes = Files.readAllBytes(Path.of(FILE_PATH));
        return bytes.length;
    }

}
