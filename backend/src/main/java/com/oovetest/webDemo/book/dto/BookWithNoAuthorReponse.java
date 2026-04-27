package com.oovetest.webDemo.book.dto;

import com.oovetest.webDemo.book.entity.BookStatus;

import java.time.LocalDateTime;


public record BookWithNoAuthorReponse (
    String title,
    BookStatus status,
    String isbn,
    LocalDateTime buyTime

    /* s暫時不保留tagnames
    Set<String> tagNames
    */
    
){}
