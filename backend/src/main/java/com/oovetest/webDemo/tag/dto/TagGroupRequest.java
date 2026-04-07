package com.oovetest.webDemo.tag.dto;

import com.oovetest.webDemo.tag.domain.TagGroupCode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TagGroupRequest {

    //廢棄；
    //僅用於快速重建
    
    @NotNull(message = "書籍狀態不能為空")
    @Schema(description = "tag群組種類", example = "RELATIONSHIP")
    private TagGroupCode code;

    @NotBlank(message = "顯示名稱不能為空")
    @Size(max = 100, message = "顯示名稱長度不能超過100字元")
    @Schema(description = "顯示名稱", example = "角色關係")
    private String displayName;
}
