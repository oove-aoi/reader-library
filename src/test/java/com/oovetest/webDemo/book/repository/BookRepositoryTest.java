package com.oovetest.webDemo.book.repository;

import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;
import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.series.model.Series;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryTest extends JpaRepository<Book, Long>, BookRepositoryCustom {

}
