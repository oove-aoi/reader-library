package com.oovetest.webDemo.book.repository;

import java.util.List;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;

public interface BookRepositoryCustom {
    List<Book> search(BookSearchCondition condition);
}
