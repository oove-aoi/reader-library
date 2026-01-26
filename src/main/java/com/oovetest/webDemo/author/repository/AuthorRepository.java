package com.oovetest.webDemo.author.repository;

import com.oovetest.webDemo.author.model.Author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findByName(String name);
} 
