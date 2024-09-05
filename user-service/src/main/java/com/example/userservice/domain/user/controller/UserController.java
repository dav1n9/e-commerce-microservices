package com.example.userservice.domain.user.controller;

import com.example.userservice.domain.user.dto.SignupRequestDto;
import com.example.userservice.domain.user.dto.UpdatePasswordRequestDto;
import com.example.userservice.domain.user.dto.UserResponseDto;
import com.example.userservice.domain.user.service.UserService;
import com.example.userservice.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto signupDto) throws Exception {
        userService.signup(signupDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(HttpServletRequest req, HttpServletResponse res,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody UpdatePasswordRequestDto request) {
        userService.updatePassword(req, res, userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }
}
