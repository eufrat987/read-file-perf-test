package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;

@RestController
public class IOController {

    public static final String FILE_PATH = "myfile.bin";

    @GetMapping("/normal")
    public int normal() throws IOException {
        System.out.println(Thread.currentThread().toString());
        return readFileBlocking();
    }

    @GetMapping("virtual")
    public CompletableFuture<Integer> virtual() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return readFileBlocking();
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
    }


//    @GetMapping("/react")
//    public Mono<Integer> reactive() {
//        Path path = Paths.get(FILE_PATH);
//        Flux<DataBuffer> data = DataBufferUtils.read(path, new DefaultDataBufferFactory(), 4096);
//        return data.reduce(0, (x,y) -> x + y.readableByteCount());
//    }
//
//    @GetMapping("/react-block")
//    public Mono<Integer> reactiveBlock() {
//        return Mono.fromCallable(this::readFileBlocking);
//    }
//
//    @GetMapping("/react-block-schedule")
//    public Mono<Integer> reactiveBlockScheduled() {
//        return Mono.fromCallable(this::readFileBlocking).subscribeOn(Schedulers.boundedElastic());
//    }
//
//    @GetMapping("/react-block-schedule-parallel")
//    public Mono<Integer> reactiveBlockScheduledParallel() {
//        return Mono.fromCallable(this::readFileBlocking).subscribeOn(Schedulers.boundedElastic()).publishOn(Schedulers.parallel());
//    }

    private int readFileBlocking() throws IOException {
        var bytes = Files.readAllBytes(Path.of(FILE_PATH));
        return bytes.length;
    }
}
