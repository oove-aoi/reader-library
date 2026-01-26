package com.oovetest.webDemo.experience.dto;

import lombok.Data;

@Data
public class ExperienceRequest {
    private String content;
    private int rating;
    private Long bookId;
}
