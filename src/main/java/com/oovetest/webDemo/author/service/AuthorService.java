package com.oovetest.webDemo.author.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.dto.AuthorWithBooksResponse;
import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import com.oovetest.webDemo.author.mapper.AuthorMapper;
import lombok.NonNull;

@Service
@Transactional
public class AuthorService {
    //getAuthorById, createAuthor, updateAuthor, deleteAuthorById
    //
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public Author getEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }

    public Author getEntityByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
    }

    //作者不帶書
    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(@NonNull Long authorId) {
        Author author = getEntityById(authorId);
        return authorMapper.toResponse(author);
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorByName(@NonNull String authorName) {
        Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
        return authorMapper.toResponse(author);
    }

    //作者帶書籍基本資料
    @Transactional(readOnly = true)
    public AuthorWithBooksResponse getAuthorWithBooksById(@NonNull Long authorId) {
        Author author = getEntityById(authorId);
        return authorMapper.toWithBooksResponse(author);
    }

    @Transactional(readOnly = true)
    public AuthorWithBooksResponse getAuthorWithBooksByName(@NonNull String authorName) {
        Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("找不到這位作者"));
        return authorMapper.toWithBooksResponse(author);
    }
    
    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());

        return authorMapper.toResponse(authorRepository.save(author));
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

    public AuthorResponse updateAuthor(@NonNull Long authorId, AuthorRequest authorRequest) {
        Author existingAuthor = getEntityById(authorId);

        existingAuthor.setName(authorRequest.getName());

        return authorMapper.toResponse(authorRepository.save(existingAuthor));
    }

    public void deleteAuthorById(@NonNull Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
