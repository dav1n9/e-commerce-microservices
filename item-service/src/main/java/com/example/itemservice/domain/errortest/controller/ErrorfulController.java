package com.example.itemservice.domain.errortest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Random;

@RestController
@RequestMapping("/errorful")
public class ErrorfulController {
    /**
     * 5%의 확률로 500 Internal Server Error 를 반환하고,
     * 나머지 95%의 경우 정상 응답(200 OK)을 반환
     */
    @GetMapping("/case1")
    public ResponseEntity<String> case1() {
        if (new Random().nextInt(100) < 5) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }

    /**
     * 0 ~ 9 초 사이 요청을 차단(10초 동안 대기) 하고, 이후 503 Service Unavailable 오류를 반환.
     * 나머지 시간에는 정상적으로 응답을 반환.
     */
    @GetMapping("/case2")
    public ResponseEntity<String> case2() {
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return ResponseEntity.status(503).body("Service Unavailable");
        }

        return ResponseEntity.ok("Normal response");
    }

    /**
     * 0 ~ 9 초 사이 500 Internal Server Error 를 반환하고,
     * 그 외의 시간에는 정상 응답을 반환.
     */
    @GetMapping("/case3")
    public ResponseEntity<String> case3() {
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }
}
