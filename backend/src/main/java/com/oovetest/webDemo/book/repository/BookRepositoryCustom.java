package com.oovetest.webDemo.book.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;

public interface BookRepositoryCustom {
    Page<Book> search(BookSearchCondition condition, Pageable pageable);
}
