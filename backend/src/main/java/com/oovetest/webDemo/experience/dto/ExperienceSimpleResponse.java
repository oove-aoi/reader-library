package com.oovetest.webDemo.experience.dto;

import lombok.Data;

@Data
public class ExperienceSimpleResponse {
    private Long id;
    private int rating;

    private Long bookId;
    private String bookTitle;
}
