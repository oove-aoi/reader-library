package com.oovetest.webDemo.book.repository;

import java.util.List;

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
    public List<Book> search(BookSearchCondition condition) {

        StringBuilder jpql = new StringBuilder(
            "SELECT DISTINCT b FROM Book b "
            + "LEFT JOIN b.tag t "
            + "LEFT JOIN b.author a "
            + "WHERE 1=1"
        );

        // ===== 原有條件 =====
        if (condition.getAuthorId() != null) {
            jpql.append(" AND a.id = :authorId");
        }

        if (condition.getTagId() != null) {
            jpql.append(" AND t.id = :tagId");
        }

        if (condition.getKeyword() != null && !condition.getKeyword().isBlank()) {
            jpql.append(" AND (");
            jpql.append(" LOWER(b.title) LIKE :keyword");
            jpql.append(" OR LOWER(b.description) LIKE :keyword");
            jpql.append(")");
        }

        // ===== 新增條件 =====

        // 作者名稱
        if (condition.getAuthorName() != null && !condition.getAuthorName().isBlank()) {
            jpql.append(" AND LOWER(a.name) LIKE :authorName");
        }

        // Tag 名稱
        if (condition.getTagName() != null && !condition.getTagName().isBlank()) {
            jpql.append(" AND LOWER(t.name) LIKE :tagName");
        }

        // 書名
        if (condition.getBookTitle() != null && !condition.getBookTitle().isBlank()) {
            jpql.append(" AND LOWER(b.title) LIKE :bookTitle");
        }

        TypedQuery<Book> query =
            entityManager.createQuery(jpql.toString(), Book.class);

        // ===== 設定參數 =====

        if (condition.getAuthorId() != null) {
            query.setParameter("authorId", condition.getAuthorId());
        }

        if (condition.getTagId() != null) {
            query.setParameter("tagId", condition.getTagId());
        }

        if (condition.getKeyword() != null && !condition.getKeyword().isBlank()) {
            query.setParameter(
                "keyword",
                "%" + condition.getKeyword().toLowerCase() + "%"
            );
        }

        if (condition.getAuthorName() != null && !condition.getAuthorName().isBlank()) {
            query.setParameter(
                "authorName",
                "%" + condition.getAuthorName().toLowerCase() + "%"
            );
        }

        if (condition.getTagName() != null && !condition.getTagName().isBlank()) {
            query.setParameter(
                "tagName",
                "%" + condition.getTagName().toLowerCase() + "%"
            );
        }

        if (condition.getBookTitle() != null && !condition.getBookTitle().isBlank()) {
            query.setParameter(
                "bookTitle",
                "%" + condition.getBookTitle().toLowerCase() + "%"
            );
        }

        return query.getResultList();
    }
}