package com.oovetest.webDemo.author.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oovetest.webDemo.author.dto.AuthorRequest;
import com.oovetest.webDemo.author.dto.AuthorResponse;
import com.oovetest.webDemo.author.dto.AuthorWithBooksResponse;
import com.oovetest.webDemo.author.mapper.AuthorMapper;
import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.exception.ValidationException;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    // 測試 getEntityById 方法

    @Test
    void getEntityById_shouldReturnAuthor_whenExists() {
        //Arrange（準備資料）
        Author author = new Author();
        author.setId(1L);

        //Mock 行為定義（Mockito 核心）
        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));

        //Act（執行目標方法）
        Author result = authorService.getEntityById(1L);

        //Assert（驗證結果）
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getEntityById_shouldThrowException_whenNotExists() {
        when(authorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authorService.getEntityById(1L);
        });
    }
    

    // 測試 getEntityByName 方法
    @Test
    void getEntityByName_souldReturnAuthor_whenExists() {
        Author author = new Author();
        author.setName("Tom");

        when(authorRepository.findByName("Tom"))
                .thenReturn(Optional.of(author));

        Author result = authorService.getEntityByName("Tom");
        assertEquals("Tom", result.getName());
    }

    @Test
    void getEntityByName_shouldThrowException_whenNotExists() {
        when(authorRepository.findByName("Tom"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authorService.getEntityByName("Tom");
        });
    }

    // 測試 getAuthorById 方法
    @Test
    void getAuthorById_shouldReturnAuthorResponse_whenExists() {
        // Arrange
        Long authorId = 1L;

        Author author = new Author();
        author.setId(authorId);
        author.setName("Tom");

        AuthorResponse response = new AuthorResponse();
        response.setId(authorId);
        response.setName("Tom");

        // <-- 這裡放 stub
        doReturn(response).when(authorMapper).toResponse(any(Author.class));

        // mock 內部呼叫
        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));
        when(authorMapper.toResponse(author))
                .thenReturn(response);

        // Act
        AuthorResponse result = authorService.getAuthorById(1L);

        // Assert
        assertEquals(authorId, result.getId());
        assertEquals("Tom", result.getName());

        // 行為驗證（重點）
        verify(authorRepository).findById(authorId);
        verify(authorMapper).toResponse(author);
    }

    @Test
    void getAuthorById_shouldThrowException_whenNotExists() {
        when(authorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authorService.getAuthorById(1L);
        });
    }

    // 測試 getAuthorWithBooksById

    @Test
    void getAuthorWithBooksById_shouldReturnAuthorWithBooksResponse_whenExists() {
        Long authorId = 1L;

        Author author = new Author();
        author.setId(authorId);
        author.setName("Tom");

        AuthorWithBooksResponse response = new AuthorWithBooksResponse();
        response.setId(authorId);
        response.setName("Tom");

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));
        when(authorMapper.toWithBooksResponse(author))
                .thenReturn(response);
        
        AuthorWithBooksResponse result = authorService.getAuthorWithBooksById(authorId);

        assertEquals(authorId, result.getId());
        assertEquals("Tom", result.getName());

        verify(authorRepository).findById(authorId);
        verify(authorMapper).toWithBooksResponse(author);
    }

    @Test
    void getAuthorWithBooksById_shouldThorwException_WhenNotExists() {
        when(authorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authorService.getAuthorById(1L);
        });


    }

    // 測試 createAuthor
    @Test
    void createAuthor_shouldReturnAuthorResponse_WhenCreateSuccess() {
        long authorId = 1L;
        AuthorRequest request = new AuthorRequest();
        request.setName("Tom");

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("Tom");

        AuthorResponse response = new AuthorResponse();
        response.setId(authorId);
        response.setName("Tom");

        when(authorRepository.save(any(Author.class)))
            .thenReturn(savedAuthor);
        when(authorMapper.toResponse(savedAuthor))
                .thenReturn(response);

        AuthorResponse result = authorService.createAuthor(request);

        assertEquals(authorId, result.getId());
        assertEquals("Tom", result.getName());

        // 驗證傳進 save 的內容（關鍵）
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());

        Author captured = captor.getValue();
        assertEquals("Tom", captured.getName());

        // 行為驗證
        verify(authorMapper).toResponse(savedAuthor);
    }

    @Test
    void createAuthor_shouldThrowException_WhenAuthorNameAlreadyExists() {
        AuthorRequest request = new AuthorRequest();
        request.setName("Tom");

        Author existingAuthor = new Author();
        existingAuthor.setName("Tom");

        when(authorRepository.findByName("Tom"))
                .thenReturn(Optional.of(existingAuthor));

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            authorService.createAuthor(request);
        });

        System.out.println("Exception message: " + ex.getMessage());

        verify(authorRepository, never()).save(any());
        verify(authorRepository).findByName("Tom");
    }

    // 測試 updateAuthor
    @Test
    void updateAuthor_shouldReturnAuthorResponse_WhenUpdateSuccess() {
        long authorId = 1L;
        AuthorRequest request = new AuthorRequest();
        request.setName("Nail");

        // 現存 Author
        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);
        existingAuthor.setName("Tom");

        Author savedAuthor = new Author();
        savedAuthor.setId(authorId);
        savedAuthor.setName("Nail");

        AuthorResponse response = new AuthorResponse();
        response.setId(authorId);
        response.setName("NewName");

        // Mock repository 行為
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(savedAuthor);

        // Mock mapper
        when(authorMapper.toResponse(savedAuthor)).thenReturn(response);

        // Act
        AuthorResponse result = authorService.updateAuthor(authorId, request);

        // Assert
        assertEquals("NewName", result.getName());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(existingAuthor);
        verify(authorMapper).toResponse(savedAuthor);
        
    }

    @Test
    void updateAuthor_shouldThrowException_WhenAuthorIdNotExists() {
        long authorId = 999L; // 不存在的 id
        AuthorRequest request = new AuthorRequest();
        request.setName("NewName");

        // mock repository 回傳空
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // 驗證拋出 NotFoundException
        assertThrows(NotFoundException.class, () -> {
            authorService.updateAuthor(authorId, request);
        });

        // repository save 不應該被呼叫
        verify(authorRepository, never()).save(any());            
    }

    // 測試 deleteAuthorById
    @Test
    void deleteAuthorById_shouldCallRepository_whenIdExists() {
        long authorId = 1L;

        // Arrange
        when(authorRepository.existsById(authorId)).thenReturn(true);

        // Act
        authorService.deleteAuthorById(authorId);

        // Assert: 驗證 repository 有被呼叫
        verify(authorRepository).deleteById(authorId);
    }

    @Test
    void deleteAuthor_shouldThrowException_WhenAuthorIdNotExists() {
        long authorId = 999L;

        when(authorRepository.existsById(authorId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            authorService.deleteAuthorById(authorId);
        });

        verify(authorRepository, never()).deleteById(anyLong());
        
    }
}
