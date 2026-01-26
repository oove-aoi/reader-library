package com.oovetest.webDemo.tag.dto;

import java.util.List;

import lombok.Data;

@Data
public class TagGroupWithTagsResponse {
    private Long groupId;
    private String groupName;
    private String groupCode;

    private List<TagsResponse> tags;
}
