package com.oovetest.webDemo.author.dto;


public record AuthorListResponse (
    long id,
    String name,
    int bookCount,
    boolean hasBooks
){

}
