package com.oovetest.webDemo.book.service;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookSearchCondition {
    @Positive(message = "作者ID必須為正整數")
    private Long authorId;
    @Positive(message = "標籤ID必須為正整數")
    private Long tagId;
    @Size(max = 255, message = "關鍵字長度不能超過255字元")
    private String keyword;
}
