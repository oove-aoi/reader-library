package com.oovetest.webDemo.book.dto;
import lombok.Data;

import java.util.Set;

import com.oovetest.webDemo.book.entity.BookStatus;

import java.time.LocalDateTime;
import java.util.HashSet;


public record BookWithNoAuthorReponse (
    String title,
    BookStatus status,
    String isbn,
    LocalDateTime buyTime

    /* s暫時不保留tagnames
    Set<String> tagNames
    */
    
){}
