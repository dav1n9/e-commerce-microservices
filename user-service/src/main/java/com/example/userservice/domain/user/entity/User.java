package com.example.userservice.domain.user.entity;

import com.example.userservice.global.common.EncryptDecryptConverter;
import com.example.userservice.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Convert(converter = EncryptDecryptConverter.class)
    @Column(nullable = false)
    private String username;

    @Convert(converter = EncryptDecryptConverter.class)
    @Column(nullable = false)
    private String address;

    @Convert(converter = EncryptDecryptConverter.class)
    @Column(nullable = false)
    private String phoneNumber;

    @Convert(converter = EncryptDecryptConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(String username, String address, String phoneNumber, String email, String password) {
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
