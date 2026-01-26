package com.oovetest.webDemo.book.repository;

import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.book.service.BookSearchCondition;
import com.oovetest.webDemo.author.model.Author;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom{
    @EntityGraph(attributePaths = "author, bookTags.tag, experience")
    public Optional<Book> findWithDetailById(Long id); // 透過ID查詢書籍並帶出關聯資料

    @EntityGraph(attributePaths = "author, bookTags.tag, experience")
    public Optional<Book> findByBookTitle(String bookTitle);

    public List<Book> findAllBooksByAuthorId(Long id);
    public List<Book> findAllByBookTags_Tag_Id(Long tagId); //透過中介表的tag_id找書
    public List<Book> findAllBooksByStatus(String status);
    public List<Book> findAllByBookTitleContaining(String keyword); //查詢書名改用模糊識別
    public boolean existsByAuthorAndBookTitle(Author author, String bookTitle);

    @EntityGraph(attributePaths = "author, bookTags.tag, experience")
    public List<Book> search(BookSearchCondition condition);//新增複合條件查詢

    public boolean existsByIsbn(String isbn);
}
