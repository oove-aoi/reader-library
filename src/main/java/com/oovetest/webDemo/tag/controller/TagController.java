package com.oovetest.webDemo.tag.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.oovetest.webDemo.tag.dto.TagResponse;
import com.oovetest.webDemo.tag.service.TagService;
import com.oovetest.webDemo.tag.dto.TagRequest;
import com.oovetest.webDemo.tag.service.TagGroupService;

@Validated
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
    @GetMapping("/tags/{tagId}")
    public ResponseEntity<TagResponse> findTagById(
        @PathVariable 
        @Positive(message = "tag ID必須為正整數")
        @Parameter(description = "tag ID", example = "1")
        Long tagId) {
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
    @GetMapping("/tags")
    public ResponseEntity<TagResponse> findTagByName(
        @RequestParam
        @NotBlank(message = "tag名稱不能為空")
        @Size(max = 100, message = "tag名稱長度不能超過100字元")
        @Parameter(description = "tag名稱", example = "技術")
        String tagName) {
            return ResponseEntity.ok(tagService.findByName(tagName));
    }

    @Operation(
        summary = "建立tag",
        tags = {"tag管理"},
        description = "建立新的tag"
    )
    @PostMapping("/tags")
    public ResponseEntity<TagResponse> createTag(
        @RequestBody
        @Valid 
        @Parameter(description = "tag資料", required = true)
        TagRequest tagRequest) {
            
            TagResponse tagResponse = tagService.createTag(tagRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(tagResponse);
            
    }

    @Operation(
        summary = "更新tag",
        tags = {"tag管理"},
        description = "更新tag資料"
    )
    @PutMapping("/tags/{tagId}")
    public ResponseEntity<TagResponse> updateTag(
        @PathVariable
        @Positive(message = "tag ID必須為正整數")
        @Parameter(description = "tag ID", example = "1")
        Long tagId,

        @RequestBody
        @Valid
        @Parameter(description = "tag資料", required = true)
        TagRequest tagRequest) {
            
            return ResponseEntity.ok(tagService.updateTag(tagId, tagRequest));
            
    }


    @Operation(
        summary = "刪除tag",
        tags = {"tag管理"},
        description = "刪除tag資料"
    )
    @DeleteMapping("/tags/{tagId}")
    public void deleteTag(
        @PathVariable
        @Positive(message = "tag ID必須為正整數")
        @Parameter(description = "tag ID", example = "1")
        Long tagId) {
            tagService.deleteTag(tagId);
    }


}
