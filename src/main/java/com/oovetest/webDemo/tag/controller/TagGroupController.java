package com.oovetest.webDemo.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.oovetest.webDemo.tag.service.TagGroupService;
import com.oovetest.webDemo.tag.dto.TagGroupRequest;
import com.oovetest.webDemo.tag.dto.TagGroupResponse;
import com.oovetest.webDemo.tag.dto.TagGroupUpdateRequest;
import com.oovetest.webDemo.tag.dto.TagGroupWithTagsResponse;

@RestController
@RequestMapping("/api")
public class TagGroupController {
    private final TagGroupService tagGroupService;


    public TagGroupController(TagGroupService tagGroupService) {
        this.tagGroupService = tagGroupService;
    }  

    @Operation(
        summary = "依tag群組名稱搜尋",
        tags = {"tag群組查詢"},
        description = "查詢tag群組資料，需提供tag群組名稱"
    )
    @GetMapping("/taggroups/name/{name}")
    public ResponseEntity<TagGroupResponse> getTagGroupByName(String name) {
        try {
            return ResponseEntity.ok(tagGroupService.findByName(name));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "依tag群組ID搜尋",
        tags = {"tag群組查詢"},
        description = "查詢tag群組資料，需提供tag群組ID"
    )
    @GetMapping("/taggroups/id/{id}")
    public ResponseEntity<TagGroupResponse> getTagGroupById(Long id) {  
        try {
            return ResponseEntity.ok(tagGroupService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }       
    }

    @Operation(
        summary = "建立tag群組",
        tags = {"tag群組管理"},
        description = "建立新的tag群組"
    )
    @PostMapping("/taggroups")
    public ResponseEntity<TagGroupResponse> createTagGroup(TagGroupRequest tagGroupRequest) {
        TagGroupResponse tagGroupResponse = tagGroupService.createTagGroup(tagGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagGroupResponse);    
    }

    @Operation(
        summary = "更新tag群組資料",
        tags = {"tag群組管理"},
        description = "更新tag群組資料，需提供tag群組ID及更新內容"
    )
    @PutMapping("/taggroups/id/{id}")
    public ResponseEntity<TagGroupResponse> updateTagGroup(Long id, TagGroupUpdateRequest tagGroupUpdateRequest) { 
        try {
            return ResponseEntity.ok(tagGroupService.updateTagGroup(id, tagGroupUpdateRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } 
    }

    @Operation(
        summary = "依tag群組ID取得所屬tag清單",
        tags = {"tag群組查詢"},
        description = "取得tag群組所屬的tag清單，需提供tag群組ID"
    )
    @GetMapping("/taggroups/{id}/tags")
    public ResponseEntity<TagGroupWithTagsResponse> getTagsByGroupId(Long groupId) {
        return ResponseEntity.ok(tagGroupService.findTagsByGroupId(groupId));
    }
    

    
    //刪除方法不提供，有需要的話可使用軟刪除的形式處理
    //創建方法也不提供


}
