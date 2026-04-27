package com.oovetest.webDemo.author.controller;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;

import com.oovetest.webDemo.author.service.AuthorService;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.exception.exceptionhandler.GlobalExceptionHandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @AfterEach
    void resetMocks() {
        Mockito.reset(authorService);
    }
    
    @Test
    public void getAuthorById_shouldReturn200_whenAuthorExists() throws Exception {
        AuthorResponse response = new AuthorResponse(1L, "Tom");

        when(authorService.getAuthorById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/authors/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Tom"));

        verify(authorService).getAuthorById(1L);
    }
    
    @Test
    public void getAuthorById_shouldReturn404_whenNotFound() throws Exception {
        when(authorService.getAuthorById(999L))
            .thenThrow(new NotFoundException("ID找不到相應的作者"));

        mockMvc.perform(get("/api/authors/999"))
               .andExpect(status().isNotFound());

        verify(authorService).getAuthorById(999L);
    }

    @Test
    public void getAuthorById_shouldReturn400_whenIdInvalid() throws Exception {

        mockMvc.perform(get("/api/authors/0")) // 不合法（0）
            .andExpect(status().isBadRequest());

        // 驗證 service 根本沒被呼叫
        verify(authorService, never()).getAuthorById(anyLong());
    }

    @Test
    public void createAuthor_shouldReturn200_whenCreateSuccess() throws Exception {
        String requestJson = 
        """
            {
                "name": "Tom"
            }
        """;

        AuthorResponse response = new AuthorResponse(1L, "Tom");

        when(authorService.createAuthor(any(AuthorRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Tom"));

        verify(authorService).createAuthor(any(AuthorRequest.class));
    }

    @Test
    public void createAuthor_shouldReturn400_whenRequestInvalid() throws Exception {
        String requestJson = 
        """
            {
                "name": ""
            }
        """;

        when(authorService.createAuthor(any(AuthorRequest.class)))
                .thenReturn(null);
        
        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
                
        verify(authorService, never()).createAuthor(any(AuthorRequest.class));
    }


    @Test
    public void updateAuthor_shouldReturn200_whenUpdateSuccess() throws Exception {
        String requestJson = 
        """
            {
                "name": "Tom"
            }
        """;

        AuthorResponse response = new AuthorResponse(1L, "Tom");

        //當使用 any()、eq() 或其他 matcher 時，該方法的所有參數都必須用 matcher，不能混用「實際值 + matcher」
        when(authorService.updateAuthor(eq(1L), any(AuthorRequest.class)))
                .thenReturn(response);
        

        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Tom"));

        verify(authorService).updateAuthor(eq(1L), any(AuthorRequest.class));        
    }
    
    //改成999也會因為後面驗證是否存在是service的工作所以他預設會是200
    @Test
    public void updateAuthor_shouldReturn404_whenIdNotFound() throws Exception  {
        String requestJson = 
        """
            {
                "name": "Tom"
            }
        """;

        when(authorService.updateAuthor(eq(999L), any(AuthorRequest.class)))
                .thenThrow(new NotFoundException("查無此作者ID"));
        

        mockMvc.perform(put("/api/authors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());

        verify(authorService, never()).updateAuthor(eq(1L), any(AuthorRequest.class));   
    }
    
    

    @Test
    public void updateAuthor_shouldReturn400_whenIdInvalid () throws Exception {
        String requestJson = 
        """
            {
                "name": ""
            }
        """;

        when(authorService.updateAuthor(eq(1L), any(AuthorRequest.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(authorService, never()).updateAuthor(eq(1L), any(AuthorRequest.class)); 
    }

    @Test
    public void deleteAuthor_shouldReturn204_whenDeleteSuccess () throws Exception {
        doNothing().when(authorService).deleteAuthorById(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());

        verify(authorService).deleteAuthorById(1L);
    }

    @Test
    public void deleteAuthor_shouldReturn404_whenIdNotFound () throws Exception {
        doThrow(new NotFoundException("查無此作者ID"))
            .when(authorService).deleteAuthorById(100L);

        mockMvc.perform(delete("/api/authors/100"))
                .andExpect(status().isNotFound());

        verify(authorService).deleteAuthorById(100L);
    }

}
