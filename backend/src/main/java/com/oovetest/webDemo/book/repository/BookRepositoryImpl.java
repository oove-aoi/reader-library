package com.oovetest.webDemo.book.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Book> search(
        BookSearchCondition condition,
        Pageable pageable
    ) {

        StringBuilder jpql = new StringBuilder("""
            SELECT DISTINCT b 
            FROM Book b 
            LEFT JOIN b.bookTags t 
            LEFT JOIN b.author a 
            WHERE 1=1
        """);

        StringBuilder countJpql = new StringBuilder("""
            SELECT COUNT(DISTINCT b)
            FROM Book b
            LEFT JOIN b.bookTags t
            LEFT JOIN b.author a
            WHERE 1=1
        """);

        Map<String, Object> params = new HashMap<>();

        // ===== 原有條件 =====
        if (condition.getAuthorId() != null) {
            jpql.append(" AND a.id = :authorId");
            countJpql.append(" AND a.id = :authorId");

            params.put("authorId", condition.getAuthorId());
        }

        if (condition.getTagId() != null) {
            jpql.append(" AND t.id = :tagId");
            countJpql.append(" AND t.id = tagId");

            params.put("tagId", condition.getTagId());
        }

        if (condition.getKeyword() != null 
            && !condition.getKeyword().isBlank()) {
            jpql.append("""
                AND (
                    LOWER(b.title) LIKE :keyword
                    OR LOWER(b.description) LIKE :keyword
                )
            """);
            
            countJpql.append("""
                AND (
                    LOWER(b.title) LIKE :keyword
                    OR LOWER(b.description) LIKE :keyword
                )
            """);

            params.put("keyword", "%" + condition.getKeyword());
        }

        // ===== 新增條件 =====

        // 作者名稱
        if (condition.getAuthorName() != null && !condition.getAuthorName().isBlank()) {
            jpql.append(" AND LOWER(a.name) LIKE :authorName");
            countJpql.append(" AND LOWER(a.name) LIKE :authorName");
        
            params.put("authorName", "%" + condition.getAuthorName().toLowerCase() + "%");
        }

        // Tag 名稱
        if (condition.getTagName() != null && !condition.getTagName().isBlank()) {
            jpql.append(" AND LOWER(t.name) LIKE :tagName");
            countJpql.append(
                    " AND LOWER(t.name) LIKE :tagName"
            );

            params.put(
                    "tagName",
                    "%" + condition.getTagName().toLowerCase() + "%"
            );
        }

        // 書名
        if (condition.getBookTitle() != null && !condition.getBookTitle().isBlank()) {
            jpql.append(
                    " AND LOWER(b.bookTitle) LIKE :bookTitle"
            );

            countJpql.append(
                    " AND LOWER(b.bookTitle) LIKE :bookTitle"
            );

            params.put(
                    "bookTitle",
                    "%" + condition.getBookTitle().toLowerCase() + "%"
            );
        }

        TypedQuery<Book> query =
            entityManager.createQuery(
                jpql.toString(), 
                Book.class
            );
        TypedQuery<Long> countQuery = 
            entityManager.createNamedQuery(
                countJpql.toString(), 
                Long.class
            );
        // ===== 設定參數 =====

        params.forEach((key, value) -> {
            query.setParameter(key, value);
            countQuery.setParameter(key, value);
        });

        // ===== 設定分頁 =====
        query.setFirstResult((int) pageable.getOffset());  // 設定 offset
        query.setMaxResults(pageable.getPageSize());    // 設定 limit

        List<Book> books = query.getResultList();
        long total = countQuery.getSingleResult();
        return new PageImpl<>(books, pageable, total);
    }
}