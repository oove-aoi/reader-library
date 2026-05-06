package com.oovetest.webDemo.author.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.oovetest.webDemo.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findByName(String name);//精準查詢
    //考慮新增模糊查詢方法
    
    public @NonNull Page<Author> findByNameContaining(@NonNull String name, @NonNull Pageable pageable);
} 
