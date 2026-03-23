package com.oovetest.webDemo.tag.repository;

import com.oovetest.webDemo.tag.model.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepositoryTest extends JpaRepository<Tag, Long> {

}
