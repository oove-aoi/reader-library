package com.oovetest.webDemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Configuration
public class CosrConfig implements WebMvcConfigurer  {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //本機開發的測試環境設定
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 允許本機端的前端請求，正式環境會需要限制實際網域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
