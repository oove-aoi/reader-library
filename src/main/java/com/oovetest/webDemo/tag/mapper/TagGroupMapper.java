package com.oovetest.webDemo.tag.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.tag.dto.TagGroupResponse;
import com.oovetest.webDemo.tag.dto.TagGroupWithTagsResponse;
import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.dto.TagsResponse;

@Component
public class TagGroupMapper {
    public TagGroupResponse toResponse(TagGroup tagGroup) {
        TagGroupResponse tagGroupResponse = new TagGroupResponse();

        tagGroupResponse.setId(tagGroup.getGroupid());
        tagGroupResponse.setCode(tagGroup.getCode().name());
        tagGroupResponse.setDisplayName(tagGroup.getTagGroupName());

        return tagGroupResponse;
    }

    public List<TagGroupResponse> toResponse(List<TagGroup> tagGroups) {
        return tagGroups.stream()
                    .map(this::toResponse)
                    .toList();
    }

    public TagGroupWithTagsResponse toTagGroupWithTagsResponse(TagGroup tagGroup) {
        TagGroupWithTagsResponse tagGroupResponse = new TagGroupWithTagsResponse();

        tagGroupResponse.setGroupId(tagGroup.getGroupid());
        tagGroupResponse.setGroupCode(tagGroup.getCode());
        tagGroupResponse.setGroupName(tagGroup.getTagGroupName());

        tagGroupResponse.setTags(
            tagGroup.getTags().stream()
                .map(tag -> {
                    TagsResponse tagResponse = new TagsResponse();
                    tagResponse.setId(tag.getId());
                    tagResponse.setName(tag.getName());
                    return tagResponse;
                })
                .toList()
        );

        return tagGroupResponse;
    }
    
    
}
