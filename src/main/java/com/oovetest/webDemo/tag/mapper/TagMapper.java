package com.oovetest.webDemo.tag.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.tag.dto.TagResponse;
import com.oovetest.webDemo.tag.model.Tag;


@Component
public class TagMapper {
    public TagResponse toResponse(Tag tag) {
        TagResponse tagResponse = new TagResponse();

        tagResponse.setId(tag.getId());
        tagResponse.setName(tag.getName());
        tagResponse.setTagGroupName(tag.getGroup().getTagGroupName());

        return tagResponse;
    }

    public List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
                   .map(this::toResponse)
                   .toList();
    }

    
}
