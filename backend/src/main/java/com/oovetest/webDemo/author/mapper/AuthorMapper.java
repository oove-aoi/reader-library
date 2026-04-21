package com.oovetest.webDemo.author.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.oovetest.webDemo.author.dto.AuthorListResponse;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.dto.AuthorWithBooksResponse;
import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.book.dto.BookWithNoAuthorReponse;

@Component
public class AuthorMapper {
    
    public AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse(author.getId(), author.getName());

        return response;
    }

    public AuthorListResponse toListResponse(Author author) {
        AuthorListResponse response = new AuthorListResponse(
            author.getId(),
            author.getName(),
            author.getBooks().size(),
            author.getBooks().isEmpty() ? false : true
        );
        return response;
    }

    public AuthorWithBooksResponse toWithBooksResponse(Author author) {
        AuthorWithBooksResponse response = new AuthorWithBooksResponse(
            author.getId(),
            author.getName(),
            author.getBooks().stream()
                .map(book -> {
                    BookWithNoAuthorReponse bookResponse = new BookWithNoAuthorReponse(
                        book.getBookTitle(),
                        book.getStatus(),
                        book.getIsbn(),
                        book.getBuytime()
                    );

                    return bookResponse;
                })
                .collect(Collectors.toSet()));

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
