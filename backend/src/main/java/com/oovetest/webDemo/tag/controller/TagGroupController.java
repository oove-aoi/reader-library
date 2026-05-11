package com.oovetest.webDemo.tag.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;

import com.oovetest.webDemo.tag.service.TagGroupService;
import com.oovetest.webDemo.tag.dto.TagGroupResponse;

import com.oovetest.webDemo.tag.dto.TagGroupWithTagsResponse;

@Validated
@RestController
@RequestMapping("/api")
public class TagGroupController {
    private final TagGroupService tagGroupService;


    public TagGroupController(TagGroupService tagGroupService) {
        this.tagGroupService = tagGroupService;
    }  

    @Operation(
        summary = "依tag群組ID搜尋",
        tags = {"tag群組查詢"},
        description = "查詢tag群組資料，需提供tag群組ID"
    )
    @GetMapping("/tagGroups/{id}")
    public ResponseEntity<TagGroupResponse> getTagGroupById(
        @PathVariable
        @Positive(message = "tag群組ID必須為正整數")
        @Parameter(description = "tag群組ID", example = "1", required = true)
        long id) {  
            return ResponseEntity.ok(tagGroupService.findById(id));
        }

    @Operation(
        summary = "取得所有的tag群組",
        tags = {"tag群組查詢"},
        description = "查詢所有tag群組資料"
    )
    @GetMapping("/tagGroups")
    public ResponseEntity<List<TagGroupResponse>> getAllTagGroups() {
        return ResponseEntity.ok(tagGroupService.findAllTagGroups());
    }

    @Operation(
        summary = "依tag群組ID取得所屬tag清單",
        tags = {"tag群組查詢"},
        description = "取得tag群組所屬的tag清單，需提供tag群組ID"
    )
    @GetMapping("/taggroups/{id}/tags")
    public ResponseEntity<TagGroupWithTagsResponse> getTagsByGroupId(
        @PathVariable
        @Positive(message = "tag群組ID必須為正整數")
        @Parameter(description = "tag群組ID", example = "1", required = true)
        long id) {
            return ResponseEntity.ok(tagGroupService.findTagsByGroupId(id));
        }

    //刪除方法不提供，有需要的話可使用軟刪除的形式處理
    //未來僅保留查詢方法，其他的創建、更新、刪除方法將不再提供
    /*
    @Operation(
        summary = "建立tag群組",
        tags = {"tag群組管理"},
        description = "建立新的tag群組"
    )
    @PostMapping("/taggroups")
    public ResponseEntity<TagGroupResponse> createTagGroup(
        @RequestBody
        @Valid
        @Parameter(description = "tag群組資料", required = true)
        TagGroupRequest tagGroupRequest) {
            TagGroupResponse tagGroupResponse = tagGroupService.createTagGroup(tagGroupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(tagGroupResponse);    
    }

    @Operation(
        summary = "更新tag群組資料",
        tags = {"tag群組管理"},
        description = "更新tag群組資料，需提供tag群組ID及更新內容"
    )
    @PutMapping("/taggroups/id/{id}")
    public ResponseEntity<TagGroupResponse> updateTagGroup(
        @PathVariable
        @Positive(message = "tag群組ID必須為正整數")
        @Parameter(description = "tag群組ID", example = "1")
        long id,

        @RequestBody
        @Valid
        @Parameter(description = "tag群組資料", required = true)
        TagGroupUpdateRequest tagGroupUpdateRequest) { 
            try {
                return ResponseEntity.ok(tagGroupService.updateTagGroup(id, tagGroupUpdateRequest));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } 
    }

    */

}
