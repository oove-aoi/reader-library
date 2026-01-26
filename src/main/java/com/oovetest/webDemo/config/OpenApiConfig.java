package com.oovetest.webDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .tags(List.of(
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("作者查詢")
                    .description("查詢作者資料的相關 API，例如取得單一作者或列出作者清單"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("作者管理")
                    .description("管理作者的相關 API，例如新增、更新、刪除作者"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("書籍查詢")
                    .description("查詢書籍資料的相關 API，例如取得單一本書或列出書籍清單"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("書籍管理")
                    .description("管理書籍的相關 API，例如新增、更新、刪除書籍"),

                new io.swagger.v3.oas.models.tags.Tag()
                    .name("心得查詢")
                    .description("查詢心得資料的相關 API，例如取得單一心得或列出心得清單"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("心得管理")
                    .description("管理心得的相關 API，例如新增、更新、刪除心得"),

                new io.swagger.v3.oas.models.tags.Tag()
                    .name("tag群組查詢")
                    .description("查詢tag群組資料的相關 API，例如取得單一tag群組或列出tag群組清單"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("tag群組管理")
                    .description("管理tag群組的相關 API，例如新增、更新、刪除tag群組"),

                new io.swagger.v3.oas.models.tags.Tag()
                    .name("tag查詢")
                    .description("查詢tag資料的相關 API，例如取得單一tag或列出tag清單"),
                new io.swagger.v3.oas.models.tags.Tag()
                    .name("tag管理")
                    .description("管理tag的相關 API，例如新增、更新、刪除tag")

                      
            ));
    }
}
