package com.oovetest.webDemo.experience.dto;



public record ExperienceSimpleResponse(
    Long id,
    int rating,
    Long bookId,
    String bookTitle
) {}
