package com.oovetest.webDemo.author.dto;

import java.util.Set;

import lombok.Data;

import com.oovetest.webDemo.book.dto.BookWithNoAuthorRequest;


@Data
public class AuthorWithBooksRequest {
    private String name;
    private Set<BookWithNoAuthorRequest> books;
}
