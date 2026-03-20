package com.oovetest.webDemo.author.controller;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Validated  //啟用方法參數驗證功能
@RestController
@RequestMapping("/api")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(
        summary = "依作者ID搜尋",
        tags = {"作者查詢"},
        description = "依據作者ID查詢作者資料"
    )
    @GetMapping("/authors/id/{authorid}")
    public ResponseEntity<AuthorResponse> getAuthorById(
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")  //id 不應為 0 或負數
        @Parameter(description = "作者ID", example = "1")
        Long authorid) {
            return ResponseEntity.ok(authorService.getAuthorById(authorid));
    }

    @Operation(
        summary = "依作者名稱查詢",
        tags = {"作者查詢"},
        description = "依據作者名稱查詢作者資料"
    )
    @GetMapping("/authors/name/{authorName}")
    public ResponseEntity<AuthorResponse> getAuthorByName(
        @PathVariable 
        @NotBlank(message = "作者名稱不可為空白") //名稱不應為空白
        @Size(max = 100, message = "作者名稱長度不可超過100字元") //名稱不應過長
        @Parameter(description = "作者名稱", example = "J.K. Rowling")
        String authorName) {
            return ResponseEntity.ok(authorService.getAuthorByName(authorName));
            
    }
    

    @Operation(
        summary = "新增一位作者",
        tags = {"作者管理"},
        description = "需提供作者名稱等欄位資訊"
    )
    @PostMapping("/authors/")
    public ResponseEntity<AuthorResponse> createAuthor(
        @RequestBody @Valid AuthorRequest authorRequest) {
            AuthorResponse authorResponse = authorService.createAuthor(authorRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(authorResponse);
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
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")  //id 不應為 0 或負數
        @Parameter(description = "作者ID", example = "1")
        Long authorid,

        @RequestBody 
        @Valid 
        AuthorRequest authorRequest){
            AuthorResponse authorResponse = authorService.updateAuthor(authorid, authorRequest);
            return ResponseEntity.status(HttpStatus.OK).body(authorResponse);
    }
    
    @Operation(
        summary = "刪除一位作者",
        tags = {"作者管理"},
        description = "需提供作者ID"
    )
    @DeleteMapping("/authors/id/{authorid}")
    public ResponseEntity<Void> deleteAuthorById(
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")  //id 不應為 0 或負數
        @Parameter(description = "作者ID", example = "1")
        Long authorid) {
            authorService.deleteAuthorById(authorid);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
