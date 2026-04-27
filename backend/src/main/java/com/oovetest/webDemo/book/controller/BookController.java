package com.oovetest.webDemo.book.controller;

import com.oovetest.webDemo.book.dto.BookRequest;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.BookSimpleResponse;
import com.oovetest.webDemo.book.dto.SeriesBookSimpleResponse;
import com.oovetest.webDemo.book.service.BookSearchCondition;
import com.oovetest.webDemo.book.service.BookService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

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
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBookById(
        @PathVariable 
        @Positive(message = "書籍ID必須為正整數") 
        @Parameter(description = "書籍ID", example = "1", required = true)
        long bookId) {
            return ResponseEntity.ok(bookService.getBookById(bookId));
        }

    
    
    @Operation(
        summary = "以作者ID查詢該作者的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供作者ID"
    )
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<SeriesBookSimpleResponse>> getBooksByAuthorId(
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")
        @Parameter(description = "作者ID", example = "1", required = true)
        long authorId) {
            List<SeriesBookSimpleResponse> books = bookService.getAllBookByAuthorId(authorId);
            return ResponseEntity.ok(books);
        }      

    @Operation(
        summary = "以標籤ID查詢該標籤的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供標籤ID"
    )
    @GetMapping("/tags/id/{tagId}/books")
    public ResponseEntity<List<SeriesBookSimpleResponse>> getBooksByTagId(
        @PathVariable 
        @Positive(message = "標籤ID必須為正整數")
        @Parameter(description = "標籤ID", example = "1", required = true)
        long tagId) {
            List<SeriesBookSimpleResponse> books = bookService.getAllBooksByTagId(tagId);
            return ResponseEntity.ok(books);
        }

    @Operation(
        summary = "新增書籍",
        tags = {"書籍管理"},
        description = "需提供title、authorName、status等欄位資訊"
    )
    @PostMapping("/books")
    public ResponseEntity<BookResponse> createBook(
        @RequestBody 
        @Valid
        @Parameter(description = "書籍資料")
        BookRequest bookRequest){
            BookResponse book = bookService.createBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }

    @Operation(
        summary = "更新書籍資料",
        tags = {"書籍管理"},
        description = "需提供title、authorName、status等欄位資訊"
    )
    @PutMapping("/books/{bookid}")
    public ResponseEntity<BookResponse> updateBookById(
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1", required = true)
        @PathVariable long bookid,

        @Valid
        @RequestBody 
        @Parameter(description = "書籍資料")
        BookRequest bookRequest){
            BookResponse book = bookService.updateBookById(bookid, bookRequest);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }

    @Operation(
        summary = "刪除書籍",
        tags = {"書籍管理"},
        description = "需提供書籍ID"
    )
    @DeleteMapping("/books/{bookid}")
    public ResponseEntity<?> deleteBook(
        @PathVariable 
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1")
        long bookid){
            bookService.deleteBookById(bookid);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    //搜索條件設計
    //後面將所有使用名稱做查詢條件的部分全部併進來
    @Operation(
        summary = "以複合條件查詢書籍",
        tags = {"書籍查詢"},
        description = "可依authorId、tagId、keyword等條件進行查詢"
    )
    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> findBooksByCondition(
        @Valid
        BookSearchCondition condition) {
            return ResponseEntity.ok(bookService.search(condition));
        }
    /* 
    目前僅底下這些查詢名稱的方法準備併入 BookSearchCondition 暫時康調
    @Operation(
        summary = "以標籤名稱查詢該標籤的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料"
    )
    @GetMapping("/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByTagName(
        @RequestParam 
        @NotBlank(message = "標籤名稱不能為空")
        @Size(max = 100, message = "標籤名稱長度不能超過100字元")
        @Parameter(description = "標籤名稱", example = "程式設計")
        String tagName) {
            List<BookSimpleResponse> books = bookService.getAllBookByTagName(tagName);
            return ResponseEntity.ok(books);
        }
    @Operation(
        summary = "以作者名稱查詢該作者的所有書籍",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供作者名稱"
    )
    @GetMapping("/books")
    public ResponseEntity<List<BookSimpleResponse>> getBooksByAuthorName(
        @RequestParam 
        @NotBlank(message = "作者名稱不能為空")
        @Size(max = 100, message = "作者名稱長度不能超過100字元")
        @Parameter(description = "作者名稱", example = "張三")
        String authorName) {
            List<BookSimpleResponse> books = bookService.getAllBooksByAuthorName(authorName);
            return ResponseEntity.ok(books);
        }

        @Operation(
        summary = "以書名查詢一本書",
        tags = {"書籍查詢"},
        description = "查詢書籍資料，需提供書名"
    )
    @GetMapping("/books")
    public ResponseEntity<BookResponse> getBookByBookName(
        @RequestParam
        @NotBlank(message = "書名不能為空") 
        @Size(max = 100, message = "書名長度不能超過100字元")
        @Parameter(description = "書名", example = "Java程式設計")
        String name) {
            return ResponseEntity.ok(bookService.getBookByTitle(name));
        }
    */
}
