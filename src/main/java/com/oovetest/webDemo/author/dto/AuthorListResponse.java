package com.oovetest.webDemo.author.dto;


import lombok.Data;

@Data
public class AuthorListResponse {

    private Long id;
    private String name;
    private int bookCount;
    private boolean hasBooks;
}
