package com.oovetest.webDemo.author.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findByName(String name);
} 
