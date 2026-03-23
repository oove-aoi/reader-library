package com.oovetest.webDemo.tag.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.model.Tag;

public interface TagGroupRepositoryTest extends JpaRepository<TagGroup, Long> {

}
