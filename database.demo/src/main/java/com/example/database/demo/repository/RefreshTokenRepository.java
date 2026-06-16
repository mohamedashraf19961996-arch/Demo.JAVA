package com.example.database.demo.repository;

import com.example.database.demo.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByEmail(String email);
    void deleteByToken(String token);
}