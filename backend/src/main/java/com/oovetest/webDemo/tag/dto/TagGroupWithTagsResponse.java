package com.oovetest.webDemo.tag.dto;

import java.util.List;

import com.oovetest.webDemo.tag.domain.TagGroupCode;

import lombok.Data;

@Data
public class TagGroupWithTagsResponse {
    private Long groupId;
    private String groupName;
    private TagGroupCode groupCode;

    private List<TagsResponse> tags;
}
