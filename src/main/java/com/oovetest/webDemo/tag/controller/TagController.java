package com.oovetest.webDemo.tag.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;

import com.oovetest.webDemo.tag.dto.TagResponse;
import com.oovetest.webDemo.tag.mapper.TagMapper;
import com.oovetest.webDemo.tag.service.TagService;
import com.oovetest.webDemo.tag.model.Tag;
import com.oovetest.webDemo.tag.dto.TagRequest;
import com.oovetest.webDemo.tag.service.TagGroupService;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService, TagGroupService tagGroupService) {
        this.tagService = tagService;
    }

    @Operation(
        summary = "依tag ID搜尋",
        tags = {"tag查詢"},
        description = "依tag ID搜尋tag資料"
    )
    @GetMapping("/tags/id/{tagId}")
    public ResponseEntity<TagResponse> findTagById(Long tagId) {
        try {
            return ResponseEntity.ok(tagService.findById(tagId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "依tag名稱搜尋",
        tags = {"tag查詢"},
        description = "依tag名稱搜尋tag資料"
    )
    @GetMapping("/tags/name/{tagName}")
    public ResponseEntity<TagResponse> findTagByName(String tagName) {
         try {
            return ResponseEntity.ok(tagService.findByName(tagName));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "建立tag",
        tags = {"tag管理"},
        description = "建立新的tag"
    )
    @PostMapping("/tags")
    public ResponseEntity<TagResponse> createTag(TagRequest tagRequest) {
        try {
            TagResponse tagResponse = tagService.createTag(tagRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(tagResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
        summary = "更新tag",
        tags = {"tag管理"},
        description = "更新tag資料"
    )
    @PutMapping("/tags/id/{tagId}")
    public ResponseEntity<TagResponse> updateTag(Long tagId, TagRequest tagRequest) {
        try {
            return ResponseEntity.ok(tagService.updateTag(tagId, tagRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(
        summary = "刪除tag",
        tags = {"tag管理"},
        description = "刪除tag資料"
    )
    @DeleteMapping("/tags/id/{tagId}")
    public void deleteTag(Long tagId) {
        tagService.deleteTag(tagId);
    }


}
