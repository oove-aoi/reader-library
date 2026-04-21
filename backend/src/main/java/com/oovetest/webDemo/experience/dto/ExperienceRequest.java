package com.oovetest.webDemo.experience.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


public record ExperienceRequest(
    String content,

    @Max(10)
    @Min(0)
    int rating,

    @Positive(message = "書籍ID必須為正整數")
    Long bookId
) {} 