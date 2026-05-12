package com.oovetest.webDemo.tag.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tag.entity.Tag;


public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> findByName(String name);

    Page<Tag> findByNameContaining(
        String tagName,
        Pageable pageable
    );
}
