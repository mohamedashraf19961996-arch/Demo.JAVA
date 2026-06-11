package com.example.demo.Model;

import java.time.LocalDateTime;

public record Content(
        Integer id,
        String title,
        String description,
        enStatus status,
        enType contentType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String url
) {

}
