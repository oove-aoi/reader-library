package com.oovetest.webDemo.experience.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oovetest.webDemo.experience.dto.ExperienceRequest;
import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.mapper.ExperienceMapper;
import com.oovetest.webDemo.experience.model.Experience;
import com.oovetest.webDemo.experience.service.ExperienceService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api")
public class ExperienceController {
    private final ExperienceService experienceService;
    private final ExperienceMapper experienceMapper;

    public ExperienceController(ExperienceService experienceService, 
                                ExperienceMapper experienceMapper) {
        this.experienceService = experienceService;
        this.experienceMapper = experienceMapper;
    }

    @Operation(
        summary = "以書籍ID查詢心得",
        tags = {"心得查詢"},
        description = "查詢心得資料，需提供書籍ID"
    )
    @GetMapping("/books/id/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> getExperienceByBookId(@PathVariable Long bookid) {
        try {
            Experience experience = experienceService.getExperienceByBookId(bookid);
            return ResponseEntity.ok(experienceMapper.toResponse(experience));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(
        summary = "以書籍ID新增心得",
        tags = {"心得管理"},
        description = "新增心得資料，需提供書籍ID"
    )
    @PostMapping("/books/id/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> createExperience(
        @PathVariable Long bookId,
        @RequestBody ExperienceRequest experienceRequest) {

        // 確認路徑變數的 bookId 與請求體內的 bookId 是否一致 
        // 可考慮使用@ControllerAdvice
        if (experienceRequest.getBookId() != null && 
            !experienceRequest.getBookId().equals(bookId)) {
            throw new IllegalArgumentException("ExperienceRequest 的 bookId" +
                                                "與路徑不一致，請確認請求內容");
        }
        
        Experience experience = experienceService.saveExperience(experienceRequest);
        return ResponseEntity.ok(experienceMapper.toResponse(experience));
    }

    @Operation(
        summary = "以書籍ID更新心得",
        tags = {"心得管理"},
        description = "更新心得資料，需提供書籍ID"
    )
    @PutMapping("/books/id/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> updateExperience(
        @PathVariable Long bookId, 
        @RequestBody ExperienceRequest experienceRequest) {

        if (experienceRequest.getBookId() != null && 
            !experienceRequest.getBookId().equals(bookId)) {
            throw new IllegalArgumentException("ExperienceRequest 的 bookId" +
                                                "與路徑不一致，請確認請求內容");
        }

        Experience experience = experienceService.updateExperience(bookId, experienceRequest);
        return ResponseEntity.ok(experienceMapper.toResponse(experience));
    }

    @Operation(
        summary = "以書籍ID刪除心得",
        tags = {"心得管理"},
        description = "刪除心得資料，需提供書籍ID"
    )
    @DeleteMapping("/books/id/{bookId}/experience")
    public ResponseEntity<?> deleteExperience(@PathVariable Long bookId) {
        try { //所有的例外處理可以考慮全改成全域例外處理
            experienceService.deleteExperience(bookId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}