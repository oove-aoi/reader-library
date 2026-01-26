package com.oovetest.webDemo.tag.repository;

import com.oovetest.webDemo.tag.model.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> findByName(String name);
}
