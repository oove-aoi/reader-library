package com.oovetest.webDemo.book.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.oovetest.webDemo.book.model.BookStatus;


@Data
public class BookResponse {
    private Long id;
    private String title;
    private String authorName;
    private BookStatus status;
    private String isbn;
    private LocalDateTime buyTime;
    private List<String> tags;

    // 心得狀態
    private boolean hasExperience;
}