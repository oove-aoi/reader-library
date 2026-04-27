package com.oovetest.webDemo.author.dto;

import java.util.Set;
import com.oovetest.webDemo.book.dto.BookWithNoAuthorReponse;

//我記得現在沒用到這個request
public record AuthorWithBooksRequest (
    String name,
    Set<BookWithNoAuthorReponse> books
) {}