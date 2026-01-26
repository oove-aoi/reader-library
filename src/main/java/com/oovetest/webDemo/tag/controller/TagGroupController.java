package com.oovetest.webDemo.tag.controller;

import com.oovetest.webDemo.tag.service.TagGroupService;

import io.swagger.v3.oas.annotations.Operation;

import com.oovetest.webDemo.tag.dto.TagGroupRequest;
import com.oovetest.webDemo.tag.dto.TagGroupResponse;
import com.oovetest.webDemo.tag.mapper.TagGroupMapper;
import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.dto.TagGroupUpdateRequest;
import com.oovetest.webDemo.tag.dto.TagGroupWithTagsResponse;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TagGroupController {
    private final TagGroupService tagGroupService;
    private final TagGroupMapper TagGroupMapper;

    public TagGroupController(TagGroupService tagGroupService, TagGroupMapper TagGroupMapper) {
        this.tagGroupService = tagGroupService;
        this.TagGroupMapper = TagGroupMapper;
    }

    @Operation(
        summary = "建立tag群組",
        tags = {"tag群組管理"},
        description = "建立新的tag群組"
    )
    @PostMapping("/taggroups")
    public TagGroupResponse createTagGroup(TagGroupRequest tagGroupRequest) {
        TagGroup tagGroup = tagGroupService.createTagGroup(tagGroupRequest);
        return TagGroupMapper.toResponse(tagGroup);
    
    }

    @Operation(
        summary = "依tag群組名稱搜尋",
        tags = {"tag群組查詢"},
        description = "查詢tag群組資料，需提供tag群組名稱"
    )
    @GetMapping("/taggroups/name/{name}")
    public TagGroupResponse getTagGroupByName(String name) {
        TagGroup tagGroup = tagGroupService.findByName(name);
        return TagGroupMapper.toResponse(tagGroup);
    }

    @Operation(
        summary = "依tag群組ID搜尋",
        tags = {"tag群組查詢"},
        description = "查詢tag群組資料，需提供tag群組ID"
    )
    @GetMapping("/taggroups/id/{id}")
    public TagGroupResponse getTagGroupById(Long id) {  
        TagGroup tagGroup = tagGroupService.findById(id);
        return TagGroupMapper.toResponse(tagGroup);
        
    }


    @Operation(
        summary = "更新tag群組資料",
        tags = {"tag群組管理"},
        description = "更新tag群組資料，需提供tag群組ID及更新內容"
    )
    @PutMapping("/taggroups/id/{id}")
    public TagGroupResponse updateTagGroup(Long id, TagGroupUpdateRequest tagGroupUpdateRequest) {  
        TagGroup tagGroup = tagGroupService.updateTagGroup(id, tagGroupUpdateRequest);
        return TagGroupMapper.toResponse(tagGroup);
    }

    @Operation(
        summary = "依tag群組ID取得所屬tag清單",
        tags = {"tag群組查詢"},
        description = "取得tag群組所屬的tag清單，需提供tag群組ID"
    )
    @GetMapping("/taggroups/{id}/tags")
    public TagGroupWithTagsResponse getTagsByGroupId(Long groupId) {
        return tagGroupService.findTagsByGroupId(groupId);
    }
    

    
    //刪除方法不提供，有需要的話可使用軟刪除的形式處理
    //創建方法也不提供


}
