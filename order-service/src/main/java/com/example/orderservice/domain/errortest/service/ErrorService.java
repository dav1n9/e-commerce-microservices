package com.example.orderservice.domain.errortest.service;

import com.example.orderservice.global.client.ItemServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {

    private final ItemServiceClient itemServiceClient;

    @Scheduled(fixedRate = 500)
    @CircuitBreaker(name = "circuit-case", fallbackMethod = "fallbackResponse")
    @Retry(name = "retry-case", fallbackMethod = "fallbackRetry")
    public void callErrorTest1() {
        try {
            String response = errorTest3();  // 다른 case 로 변경 가능
            log.info("Response: " + response);
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
            throw new IllegalStateException();
        }
    }

    public String errorTest1(){
        return itemServiceClient.case1();
    }

    public String errorTest2(){
        return itemServiceClient.case2();
    }

    public String errorTest3(){
        return itemServiceClient.case3();
    }

    private void fallbackResponse(Exception e){
        log.info("[fallback] : " + e.getMessage());
    }

    private void fallbackRetry(Exception e){
        log.info("[retry-fallback] : " + e.getMessage());
    }
}
