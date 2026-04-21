package com.oovetest.webDemo.author.dto;

import java.util.Set;


import com.oovetest.webDemo.book.dto.BookWithNoAuthorReponse;


public record AuthorWithBooksRequest (
    String name,
    Set<BookWithNoAuthorReponse> books
) {}