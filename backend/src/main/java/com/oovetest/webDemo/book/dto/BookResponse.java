package com.oovetest.webDemo.book.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oovetest.webDemo.book.entity.BookStatus;

public record BookResponse (
    Long id,
    String title,
    String authorName,
    BookStatus status,
    String isbn,
    Integer volume,
    LocalDateTime buyTime,
    List<String> tags,

    // 心得狀態
    boolean hasExperience
){}