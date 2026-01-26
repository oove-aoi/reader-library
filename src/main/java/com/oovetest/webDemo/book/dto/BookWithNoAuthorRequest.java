package com.oovetest.webDemo.book.dto;
import lombok.Data;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;
import com.oovetest.webDemo.book.model.BookStatus;

@Data
public class BookWithNoAuthorRequest {
    private String title;
    private BookStatus status;
    private String isbn;
    private LocalDateTime buyTime;

    // 用 Set<String> 代替 Tag 實體
    private Set<String> tagNames = new HashSet<>();

}
