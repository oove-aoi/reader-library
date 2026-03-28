package com.oovetest.webDemo.book.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;

import jakarta.persistence.EntityManager;


@DataJpaTest
@ActiveProfiles("test")
@EntityScan("com.oovetest.webDemo")
@TestPropertySource(locations = "classpath:application-test.properties")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Test
    void debug_em() {
        System.out.println(em);
    }

    @Test
    void debug_datasource() throws Exception {
        System.out.println(dataSource.getConnection().getMetaData().getURL());
    }

    @Test
    void search_shouldReturnBooks_whenMatchCondition() {
        // Arrange
        Author author = new Author();
        author.setName("Test Author");
        em.persist(author);

        Book book = new Book();
        book.setBookTitle("Spring Boot Guide");
        book.setAuthor(author);
        em.persist(book);

        em.flush();
        em.clear();

        BookSearchCondition condition = new BookSearchCondition();
        condition.setKeyword("Spring");

        // Act
        List<Book> result = bookRepository.search(condition);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBookTitle()).isEqualTo("Spring Boot Guide");
    }
}

