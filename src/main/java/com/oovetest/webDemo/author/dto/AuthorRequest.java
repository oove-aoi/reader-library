package com.oovetest.webDemo.author.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotBlank(message = "作者名稱不可為空")
    @Size(max = 100, message = "作者名稱長度不可超過100字")
    private String name;
}