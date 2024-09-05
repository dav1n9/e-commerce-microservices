package com.example.userservice.domain.user.service;

import com.example.userservice.domain.user.dto.SignupRequestDto;
import com.example.userservice.domain.user.dto.UpdatePasswordRequestDto;
import com.example.userservice.domain.user.dto.UserResponseDto;
import com.example.userservice.domain.user.entity.User;
import com.example.userservice.domain.user.repository.UserRepository;
import com.example.userservice.global.client.OrderServiceClient;
import com.example.userservice.global.exception.BusinessException;
import com.example.userservice.global.exception.ErrorCode;
import com.example.userservice.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupDto) throws Exception {

        Optional<User> checkEmail = userRepository.findByEmail(signupDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = userRepository.save(signupDto.toEntity(passwordEncoder.encode(signupDto.getPassword())));
        orderServiceClient.createDefaultWishlist(user.getId());

    }

    @Transactional
    public void updatePassword(HttpServletRequest req, HttpServletResponse res,
                               Long userId, UpdatePasswordRequestDto pwDto) {

        User user = findUserById(userId);

        if (!passwordEncoder.matches(pwDto.getCurrentPassword(), user.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);

        user.setPassword(passwordEncoder.encode(pwDto.getNewPassword()));

        jwtUtil.deleteToken(req, res);
    }

    public UserResponseDto findById(Long userId) {
        User user = findUserById(userId);
        return new UserResponseDto(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}