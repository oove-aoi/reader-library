package com.oovetest.webDemo.experience.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExperienceResponse {
    private Long bookId;
    private String bookTitle;
    
    private String content;
    private int rating;
    private LocalDateTime createdAt;

    
}

