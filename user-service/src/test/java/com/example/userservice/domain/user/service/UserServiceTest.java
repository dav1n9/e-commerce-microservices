package com.example.userservice.domain.user.service;

import com.example.userservice.domain.user.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @Rollback(false)
    public void 회원_100명_생성() throws Exception {
        for (int i = 1; i <= 100; i++) {
            userService.signup(new SignupRequestDto("name" + i, "address", "0100000",
                    "mail" + i + "@test.com", "1234"));

        }
    }
}