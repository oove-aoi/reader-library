package com.oovetest.webDemo.book.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;

public interface BookRepositoryCustom {
    List<Book> search(BookSearchCondition condition);
}
