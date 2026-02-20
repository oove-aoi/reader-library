package com.oovetest.webDemo.author.dto;

import java.util.Set;

import com.oovetest.webDemo.book.dto.BookWithNoAuthorReponse;

import lombok.Data;

@Data
public class AuthorWithBooksResponse {
    private Long id;
    private String name;
    private Set<BookWithNoAuthorReponse> books;
}
