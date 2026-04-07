package com.oovetest.webDemo.tag.dto;

import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class TagRequest {

    

    @NotBlank(message = "tag名稱不能為空")
    @Size(max = 100, message = "tag名稱長度不能超過100字元")
    @Schema(description = "tag", example = "BL")
    private String name;

    @Positive(message = "tag group ID必須為正整數")
    @Schema(description = "tag group ID", example = "3")
    private Long groupId;
}
