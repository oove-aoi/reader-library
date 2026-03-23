package com.oovetest.webDemo.author.controller;

import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.mapper.AuthorMapper;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import com.oovetest.webDemo.author.service.AuthorService;
import com.oovetest.webDemo.exception.GlobalExceptionHandler;
import com.oovetest.webDemo.exception.NotFoundException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(AuthorController.class)
@Import(GlobalExceptionHandler.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService; 

    @InjectMocks
    private AuthorController authorController;
    
    @Test
    public void getAuthorById_shouldReturn200_whenAuthorExists() throws Exception {
        System.out.print(authorService.getClass());
        
        AuthorResponse response = new AuthorResponse();
        response.setId(1L);
        response.setName("Tom");

        when(authorService.getAuthorById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/authors/id/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Tom"));

        verify(authorService).getAuthorById(1L);
    }
    
    @Test
    void getAuthorById_shouldReturn404_whenNotFound() throws Exception {
        when(authorService.getAuthorById(999L))
            .thenThrow(new NotFoundException("ID找不到相應的作者"));

        mockMvc.perform(get("/api/authors/id/999"))
               .andExpect(status().isNotFound());

        verify(authorService).getAuthorById(999L);
    }

    @Test
    void getAuthorById_shouldReturn400_whenIdInvalid() throws Exception {

        mockMvc.perform(get("/api/authors/id/0")) // 不合法（0）
            .andExpect(status().isBadRequest());

        // 驗證 service 根本沒被呼叫
        verify(authorService, never()).getAuthorById(anyLong());
    }
}
