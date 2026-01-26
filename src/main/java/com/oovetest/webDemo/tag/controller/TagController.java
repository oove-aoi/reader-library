package com.oovetest.webDemo.tag.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.oovetest.webDemo.tag.service.TagService;

import io.swagger.v3.oas.annotations.Operation;

import com.oovetest.webDemo.tag.dto.TagResponse;
import com.oovetest.webDemo.tag.mapper.TagMapper;
import com.oovetest.webDemo.tag.model.Tag;
import com.oovetest.webDemo.tag.dto.TagRequest;
import com.oovetest.webDemo.tag.service.TagGroupService;

@RestController
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;
    
    public TagController(TagService tagService, TagGroupService tagGroupService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @Operation(
        summary = "依tag ID搜尋",
        tags = {"tag查詢"},
        description = "依tag ID搜尋tag資料"
    )
    @GetMapping("/tags/id/{tagId}")
    public TagResponse findTagById(Long tagId) {
        Tag tag = tagService.findById(tagId);
        return tagMapper.toResponse(tag);
    }

    @Operation(
        summary = "依tag名稱搜尋",
        tags = {"tag查詢"},
        description = "依tag名稱搜尋tag資料"
    )
    @GetMapping("/tags/name/{tagName}")
    public TagResponse findTagByName(String tagName) {
        Tag tag = tagService.findByName(tagName);
        return tagMapper.toResponse(tag);
    }

    @Operation(
        summary = "建立tag",
        tags = {"tag管理"},
        description = "建立新的tag"
    )
    @PostMapping("/tags")
    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = tagService.createTag(tagRequest);
        return tagMapper.toResponse(tag);
    }

    @Operation(
        summary = "更新tag",
        tags = {"tag管理"},
        description = "更新tag資料"
    )
    @PutMapping("/tags/id/{tagId}")
    public TagResponse updateTag(Long tagId, TagRequest tagRequest) {
        Tag tag = tagService.updateTag(tagId, tagRequest);
        return tagMapper.toResponse(tag);
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
