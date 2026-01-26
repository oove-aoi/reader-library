package com.oovetest.webDemo.author.service;

import org.springframework.stereotype.Service;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import lombok.NonNull;

@Service
public class AuthorService {
    //getAuthorById, createAuthor, updateAuthor, deleteAuthorById
    //
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //作者不帶書
    public Author getAuthorById(@NonNull Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }

    public Author getAuthorByName(@NonNull String authorName) {
        return authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }

    //作者帶書籍基本資料
    public Author getAuthorWithBooksById(@NonNull Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }

    public Author getAuthorWithBooksByName(@NonNull String authorName) {
        return authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }
    
    public Author createAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
            
        return authorRepository.save(author);
    }

    /* 先暫時不寫攜帶書籍資料的方法
    //新增作者同時帶書籍基本資料
    public Author createAuthorAndBooks(AuthorWithBooksRequest authorWithBooksRequest) {
        Author author = new Author();
        
        author.setName(authorWithBooksRequest.getName());
        if (authorWithBooksRequest.getBooks() != null) {
            for (var bookRequest : authorWithBooksRequest.getBooks()) {
                Book book = new Book();
                book.setBookTitle(bookRequest.getTitle());
                if (bookRequest.getBuyTime() != null) {
                    book.setBuytime(bookRequest.getBuyTime());
                }
                if (bookRequest.getStatus() != null) {
                    book.setStatus(bookRequest.getStatus());
                }
                if (bookRequest.getIsbn() != null) {
                    book.setIsbn(bookRequest.getIsbn());
                }
                book.setAuthor(author);
                author.getBooks().add(book);
            }
        }

        return authorRepository.save(author);
    }

    */

    public Author updateAuthor(@NonNull Long authorId, AuthorRequest authorRequest) {
        Author existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));

        existingAuthor.setName(authorRequest.getName());

        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthorById(@NonNull Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
