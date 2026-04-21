package com.oovetest.webDemo.book.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.oovetest.webDemo.book.dto.SeriesBookSimpleResponse;
import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.BookSimpleResponse;

@Component
public class BookMapper {

    // 將 Book 實體轉換為 BookResponse DTO
    public BookResponse toResponse(Book book) {
        return new BookResponse(
            book.getId(),
            book.getBookTitle(),
            book.getAuthor().getName(),
            book.getStatus(),
            book.getIsbn(),
            book.getBuytime(),
            book.getBookTags().stream()
                .map(bt -> bt.getTag().getName())
                .toList(),
            book.getExperience() != null
        );
    }

    // 批次轉換
    public List<BookResponse> toResponse(List<Book> books){
        return books.stream()
                    .map(this::toResponse)
                    .toList();
    }

    public BookSimpleResponse toSimpleResponse(Book book) {
        BookSimpleResponse response = new BookSimpleResponse(book.getId(), book.getBookTitle());

        return response;
    }

    public List<BookSimpleResponse> toSimpleResponse(List<Book> books){
        return books.stream()
                    .map(this::toSimpleResponse)
                    .toList();
    }

    public SeriesBookSimpleResponse toSeriesBookSimpleResponse(Book book) {
        return new SeriesBookSimpleResponse(
            book.getId(),
            book.getBookTitle(),
            book.getSeries() != null ? book.getSeries().getTitle() : null,
            book.getSeries() != null ? book.getVolume() : null
        );
    }

    public List<SeriesBookSimpleResponse> toSeriesBookSimpleResponse(List<Book> books){
        return books.stream()
                    .map(this::toSeriesBookSimpleResponse)
                    .toList();
    }
}
