package com.oovetest.webDemo.book.dto;

import lombok.Data;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;
import com.oovetest.webDemo.book.model.BookStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Data
public class BookRequest {
    @NotBlank(message = "書名不能為空")
    @Size(max = 100, message = "書名長度不能超過100字元")
    private String title;

    @NotBlank(message = "作者名稱不能為空")
    @Size(max = 100, message = "作者名稱長度不能超過100字元")
    private String authorName;

    @NotBlank(message = "書籍狀態不能為空")
    private BookStatus status;

    private String isbn;

    private LocalDateTime buyTime;

    @NotBlank(message = "系列名稱不可為空")
    @Size(max = 100, message = "系列名稱長度不能超過100字元")
    private String seriesName;
    
    @Positive(message = "卷數必須為正整數")
    private Integer volume;

    // 用 Set<String> 代替 Tag 實體
    private Set<String> tagNames = new HashSet<>();
}
