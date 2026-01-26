package com.oovetest.webDemo.book.service;

import lombok.Data;

@Data
public class BookSearchCondition {
    private Long authorId;
    private Long tagId;
    private String keyword;
}
