package com.oovetest.webDemo.book.dto;

import lombok.Data;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;
import com.oovetest.webDemo.book.model.BookStatus;

@Data
public class BookRequest {
    private String title;
    private String authorName;
    private BookStatus status;
    private String isbn;
    private LocalDateTime buyTime;
    private String seriesName;
    private Integer volume;

    // 用 Set<String> 代替 Tag 實體
    private Set<String> tagNames = new HashSet<>();
}
