package com.oovetest.webDemo.author.dto;

import java.util.Set;

import lombok.Data;

import com.oovetest.webDemo.book.dto.BookWithNoAuthorReponse;


@Data
public class AuthorWithBooksRequest {
    /* 暫不使用 */
    private String name;
    private Set<BookWithNoAuthorReponse> books;
}
