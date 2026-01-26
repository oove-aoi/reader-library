package com.oovetest.webDemo.book.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.BookSimpleResponse;
import com.oovetest.webDemo.book.model.Book;

@Component
public class BookMapper {

    // 將 Book 實體轉換為 BookResponse DTO
    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        
        response.setId(book.getId());
        response.setTitle(book.getBookTitle());
        response.setAuthorName(book.getAuthor().getName());
        response.setStatus(book.getStatus());
        response.setIsbn(book.getIsbn());
        response.setBuyTime(book.getBuytime());
        // 轉換 tags 為字串列表
        response.setTags(
            book.getBookTags().stream()
                .map(bt -> bt.getTag().getName())
                .toList()
        );
        // 心得狀態
        response.setHasExperience(book.getExperience() == null ? false : true);
        return response;
    }

    // 批次轉換
    public List<BookResponse> toResponse(List<Book> books){
        return books.stream()
                    .map(this::toResponse)
                    .toList();
    }

    public BookSimpleResponse toSimpleResponse(Book book) {
        BookSimpleResponse response = new BookSimpleResponse();
        
        response.setId(book.getId());
        response.setTitle(book.getBookTitle());
        
        return response;
    }

    public List<BookSimpleResponse> toSimpleResponse(List<Book> books){
        return books.stream()
                    .map(this::toSimpleResponse)
                    .toList();
    }
}
