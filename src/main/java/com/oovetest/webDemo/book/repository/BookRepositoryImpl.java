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
    //實作複合條件查詢
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> search(BookSearchCondition condition) {
        StringBuilder jpql = new StringBuilder(
            "SELECT distinct b from Book b "
            + "LEFT JOIN b.tag t "
            + "WHERE 1=1"
        );

        if (condition.getAuthorId() != null) {
            jpql.append(" AND b.author.id = :authorId");
        }

        if (condition.getTagId() != null) {
            jpql.append(" AND t.id = :tagId");
        }

        if (condition.getKeyword() != null && !condition.getKeyword().isBlank()) {
            jpql.append(" and (");
            jpql.append(" lower(b.title) like :keyword");
            jpql.append(" or lower(b.description) like :keyword");
            jpql.append(" )");
        }

        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);

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

        return query.getResultList();
    }
}
