package com.oovetest.webDemo.book.controller;

import com.oovetest.webDemo.book.dto.BookRequest;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.BookSimpleResponse;
import com.oovetest.webDemo.book.mapper.BookMapper;
import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;
import com.oovetest.webDemo.book.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
        summary = "以ID查詢一本書",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供書籍ID"
    )
    @GetMapping("/books/id/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable 
        @Positive(message = "書籍ID必須為正整數") 
        @Parameter(description = "書籍ID", example = "123456789")
        Long bookid) {
            try {
                return ResponseEntity.ok(bookService.getBookById(bookid));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @Operation(
        summary = "以書名查詢一本書",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供書名"
    )
    @GetMapping("/books/name/{bookName}")
    public ResponseEntity<BookResponse> getBook(
        @PathVariable
        @NotBlank(message = "書名不能為空") 
        @Size(max = 100, message = "書名長度不能超過100字元")
        @Parameter(description = "書名", example = "Java程式設計")
        String bookName) {
            try {
                return ResponseEntity.ok(bookService.getBookByTitle(bookName));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }
    
    @Operation(
        summary = "以作者ID查詢該作者的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供作者ID"
    )
    @GetMapping("/authors/id/{authorId}/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByAuthor(
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")
        @Parameter(description = "作者ID", example = "123456789")
        Long authorId) {
            try {
                List<BookSimpleResponse> books = bookService.getAllBookByAuthorId(authorId);
                return ResponseEntity.ok(books);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @Operation(
        summary = "以作者名稱查詢該作者的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供作者名稱"
    )
    @GetMapping("/authors/name/{authorName}/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByAuthor(
        @PathVariable 
        @NotBlank(message = "作者名稱不能為空")
        @Size(max = 100, message = "作者名稱長度不能超過100字元")
        @Parameter(description = "作者名稱", example = "張三")
        String authorName) {
            try {
                List<BookSimpleResponse> books = bookService.getAllBooksByAuthorName(authorName);
                return ResponseEntity.ok(books);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @Operation(
        summary = "以標籤ID查詢該標籤的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供標籤ID"
    )
    @GetMapping("/tags/id/{tagId}/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByTag(
        @PathVariable 
        @Positive(message = "標籤ID必須為正整數")
        Long tagId) {
            try {
                List<BookSimpleResponse> books = bookService.getAllBooksByTagId(tagId);
                return ResponseEntity.ok(books);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @Operation(
        summary = "以標籤名稱查詢該標籤的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料"
    )
    @GetMapping("/tags/name/{tagName}/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByTagName(
        @PathVariable 
        @NotBlank(message = "標籤名稱不能為空")
        @Size(max = 100, message = "標籤名稱長度不能超過100字元")
        @Parameter(description = "標籤名稱", example = "程式設計")
        String tagName) {
            try {
                List<BookSimpleResponse> books = bookService.getAllBookByTagName(tagName);
                return ResponseEntity.ok(books);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @Operation(
        summary = "新增書籍",
        tags = {"書籍管理"},
        description = "需提供title、authorName、status等欄位資訊"
    )
    @PostMapping("/books")
    public ResponseEntity<BookSimpleResponse> createBook(
        @RequestBody 
        @Valid
        @Parameter(description = "書籍資料", example = "{\"title\":\"Java程式設計\",\"authorName\":\"張三\",\"status\":\"AVAILABLE\",\"isbn\":\"978-0134685991\"}")
        BookRequest bookRequest){
            BookSimpleResponse book = bookService.createBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(book);
    }

    @Operation(
        summary = "更新書籍資料",
        tags = {"書籍管理"},
        description = "需提供title、authorName、status等欄位資訊"
    )
    @PutMapping("/books/id/{bookid}")
    public ResponseEntity<BookResponse> updateBook(
        @Positive(message = "作者ID必須為正整數")
        @Parameter(description = "作者ID", example = "123456789")
        @PathVariable Long bookid,

        @Valid
        @RequestBody 
        @Parameter(description = "書籍資料", example = "{\"title\":\"Java程式設計\",\"authorName\":\"張三\",\"status\":\"AVAILABLE\",\"isbn\":\"978-0134685991\"}")
        BookRequest bookRequest){
        try {
            BookResponse book = bookService.updateBookById(bookid, bookRequest);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "刪除書籍",
        tags = {"書籍管理"},
        description = "需提供書籍ID"
    )
    @DeleteMapping("/books/id/{bookid}")
    public ResponseEntity<?> deleteBook(
        @PathVariable 
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "123456789")
        Long bookid){
        try { //所有的例外處理可以考慮全改成全域例外處理
            bookService.deleteBookById(bookid);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //搜索條件設計
    @Operation(
        summary = "以複合條件查詢書籍",
        tags = {"書籍查詢"},
        description = "可依authorId、tagId、keyword等條件進行查詢"
    )
    @GetMapping("/books/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
        @Valid
        BookSearchCondition condition) {
        return ResponseEntity.ok(bookService.search(condition));
    }
}
