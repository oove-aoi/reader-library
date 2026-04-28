package com.oovetest.webDemo.author.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.author.dto.AuthorListResponse;
import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.dto.AuthorWithBooksResponse;
import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.exception.ValidationException;
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

    public Author getEntityById(long id) { //可以改成findAuthorOrThrow
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("查無此作者ID"));
    }

    public Author getEntityByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("查無此作者名稱"));
    }

    public Page<AuthorListResponse> getAllAuthor(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toListResponse);
    }

    //作者不帶書
    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(@NonNull Long authorId) {
        Author author = getEntityById(authorId);
        return authorMapper.toResponse(author);
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorByName(@NonNull String authorName) {
        Author author = getEntityByName(authorName);
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
        Author author = getEntityByName(authorName);
        return authorMapper.toWithBooksResponse(author);
    }
    
    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        authorRepository.findByName(authorRequest.name())
                .ifPresent(a -> {
                    throw new ValidationException("已存在相同作者名稱");
                });
                
        Author author = new Author();
        author.setName(authorRequest.name());

        return authorMapper.toResponse(authorRepository.save(author));
    }

    /* 先暫時不寫攜帶書籍資料的方法
    //新增作者同時帶書籍基本資料
    public Author createAuthorAndBooks(AuthorWithBooksRequest authorWithBooksRequest) {
        Author author = new Author();
        
        author.setName(authorWithBooksRequest.name());
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
        existingAuthor.setName(authorRequest.name());

        return authorMapper.toResponse(authorRepository.save(existingAuthor));
    }

    public void deleteAuthorById(@NonNull Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NotFoundException("查無此作者ID");
        }
        authorRepository.deleteById(authorId);
    }
}
