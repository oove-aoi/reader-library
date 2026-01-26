package com.oovetest.webDemo.author.controller;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.mapper.AuthorMapper;
import com.oovetest.webDemo.author.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;

import com.oovetest.webDemo.author.model.Author;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Operation(
        summary = "依作者ID搜尋",
        tags = {"作者查詢"},
        description = "查詢作者資料，需提供作者ID"
    )
    @GetMapping("/authors/id/{authorid}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long authorid) {
        try {
            Author author = authorService.getAuthorById(authorid);
            return ResponseEntity.ok(authorMapper.toResponse(author));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "依作者名稱查詢",
        tags = {"作者查詢"},
        description = "查詢作者資料，需提供作者名稱"
    )
    @GetMapping("/authors/name/{authorName}")
    public ResponseEntity<AuthorResponse> getAuthorByName(@PathVariable String authorName) {
        try {
            Author author = authorService.getAuthorByName(authorName);
            return ResponseEntity.ok(authorMapper.toResponse(author));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    

    @Operation(
        summary = "新增一位作者",
        tags = {"作者管理"},
        description = "需提供作者名稱等欄位資訊"
    )
    @PostMapping("/authors/")
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest) {
        Author author = authorService.createAuthor(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.toResponse(author));
    }
    /* 先暫時不寫攜帶書籍資料的方法
    @Operation(
        summary = "新增作者並同時建立書籍",
        tags = {"作者管理"},
        description = "建立作者，並一次建立其名下書籍"
    )
    @PostMapping("/authors/with-books")
    public ResponseEntity<AuthorResponse> createAuthorAndBooks(@RequestBody AuthorWithBooksRequest authorWithBooksRequest) {
        Author author = authorService.createAuthorAndBooks(authorWithBooksRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.toResponse(author));
    }
    */
    
    @Operation(
        summary = "更新一位作者資料",
        tags = {"作者管理"},
        description = "需提供作者名稱等欄位資訊"
    )
    @PutMapping("/authors/id/{authorid}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long authorid,
            @RequestBody AuthorRequest authorRequest){
        try {
            Author author = authorService.updateAuthor(authorid, authorRequest);
            return ResponseEntity.status(HttpStatus.OK).body(authorMapper.toResponse(author));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Operation(
        summary = "刪除一位作者",
        tags = {"作者管理"},
        description = "需提供作者ID"
    )
    @DeleteMapping("/authors/id/{authorid}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long authorid) {
        authorService.deleteAuthorById(authorid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
