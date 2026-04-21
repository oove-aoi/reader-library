package com.oovetest.webDemo.experience.dto;

import java.time.LocalDateTime;


public record ExperienceResponse(
    Long bookId,
    String bookTitle,
    String content,
    int rating,
    LocalDateTime createdAt
) {} 

