package com.oovetest.webDemo.author.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.author.dto.AuthorListResponse;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.model.Author;

@Component
public class AuthorMapper {
    
    public AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();

        response.setId(author.getId());
        response.setName(author.getName());

        return response;
    }

    public AuthorListResponse toListResponse(Author author) {
        AuthorListResponse response = new AuthorListResponse();

        response.setId(author.getId());
        response.setName(author.getName());
        response.setBookCount(author.getBooks().size());
        response.setHasBooks(author.getBooks().isEmpty() ? false : true);


        return response;
    }

    public List<AuthorResponse> toResponse(List<Author> authors) {
        return authors.stream()
                      .map(this::toResponse)
                      .toList();
    }

    public List<AuthorListResponse> toListResponse(List<Author> authors) {
        return authors.stream()
                      .map(this::toListResponse)
                      .toList();
    }
                      
}
