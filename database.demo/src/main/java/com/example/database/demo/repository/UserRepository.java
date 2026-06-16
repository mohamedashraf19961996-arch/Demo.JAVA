package com.example.database.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.database.demo.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}