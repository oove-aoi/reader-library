package com.oovetest.webDemo.author.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class AuthorRequest {
    @NotBlank(message = "作者名稱不可為空")
    @Size(max = 100, message = "作者名稱長度不可超過100字")
    @Schema(example = "J.K. Rowling")
    private String name;
}