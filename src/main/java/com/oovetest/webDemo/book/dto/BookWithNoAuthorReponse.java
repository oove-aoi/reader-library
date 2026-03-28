package com.oovetest.webDemo.book.dto;
import lombok.Data;

import java.util.Set;

import com.oovetest.webDemo.book.entity.BookStatus;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
public class BookWithNoAuthorReponse {
    private String title;
    private BookStatus status;
    private String isbn;
    private LocalDateTime buyTime;

    // 用 Set<String> 代替 Tag 實體
    private Set<String> tagNames = new HashSet<>();

}
