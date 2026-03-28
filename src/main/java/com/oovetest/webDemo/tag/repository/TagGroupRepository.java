package com.oovetest.webDemo.tag.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tag.entity.Tag;
import com.oovetest.webDemo.tag.entity.TagGroup;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    public Optional<TagGroup> findByTagGroupName(String tagGroupName);
    public List<Tag> findTagsByGroupid(Long groupid);
}
