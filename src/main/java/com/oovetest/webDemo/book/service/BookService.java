package com.oovetest.webDemo.book.service;

import com.oovetest.webDemo.book.dto.BookRequest;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.tag.model.Tag;
import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.author.repository.AuthorRepository;
import com.oovetest.webDemo.tag.repository.TagRepository;
import com.oovetest.webDemo.book.mapper.BookMapper;


import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookMapper bookMapper;


    //考慮未來bookService的重構，改成使用其他必要模組的service而非Repository
    public BookService(BookRepository bookRepository, 
            AuthorRepository authorRepository,
            TagRepository tagRepository,
            BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional(readOnly = true)
    public Book getBookById(@NonNull Long bookId) {
        //findById 回傳 Optional<Book> 所以後面必須加上orElseThrow

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("找不到這本書"));
    }

    @Transactional(readOnly = true)
    public Book getBookByTitle(@NonNull String bookName) {
        return bookRepository.findByBookTitle(bookName)
                .orElseThrow(() -> new RuntimeException("找不到這本書"));
    }

    public Book createBook(BookRequest bookRequest) {
        //雙邊關係，關聯欄位得自己手動更新
        Book book = new Book();
        Author author = authorRepository.findByName(bookRequest.getAuthorName())
            .orElseGet(() -> {
                //log.info("author not found, creating new one");
                Author newAuthor = new Author();
                newAuthor.setName(bookRequest.getAuthorName());
                return authorRepository.save(newAuthor);
            });
        
        if (bookRepository.existsByAuthorAndBookTitle(author, bookRequest.getTitle())) {
            throw new IllegalStateException("該作者已存在同名書籍");
        }
        if(bookRequest.getIsbn() != null && bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new IllegalStateException("已存在相同ISBN的書籍");
        }
        if (bookRequest.getIsbn() != null && bookRequest.getIsbn().isBlank()) {
            book.setIsbn(null);
        }
        book.setBookTitle(bookRequest.getTitle());
        book.setStatus(bookRequest.getStatus());
        book.setBuytime(LocalDateTime.now());
        for (String tagName : bookRequest.getTagNames()) {
            Tag tag = tagRepository.findByName(tagName)
                                    .orElseThrow(() ->
                                        new IllegalArgumentException("Tag not found: " + tagName)
                                    );
            book.addTag(tag);  // helper method 自動生成 BookTag
        }

        author.addBook(book); 

        // 儲存到資料庫
        return bookRepository.save(book);
    }

    public Book updateBookById(@NonNull Long bookId, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("找不到這本書"));

        Author author = authorRepository.findByName(bookRequest.getAuthorName())
            .orElseThrow(() -> new RuntimeException("找不到這位作者"));

        author.addBook(existingBook);    

        existingBook.setBookTitle(bookRequest.getTitle());
        existingBook.setStatus(bookRequest.getStatus());
        for (String tagName : bookRequest.getTagNames()) {
            Tag tag = tagRepository.findByName(tagName)
                                    .orElseThrow(() ->
                                        new IllegalArgumentException("Tag not found: " + tagName)
                                    );
            existingBook.addTag(tag);  // helper method 自動生成 BookTag
        }

        return bookRepository.save(existingBook);
    }

    public void deleteBookById(@NonNull Long bookId) {
        Book deleteBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("找不到這本書"));

        Author author = authorRepository.findById(deleteBook.getAuthor().getId())
            .orElseThrow(() -> new RuntimeException("找不到這位作者"));

        author.removeBook(deleteBook);
        bookRepository.deleteById(bookId);
    }


    /*
    使用作者名、ID搜索書籍列表的方法、
    使用書籍名稱、閱讀狀況、書籍標籤搜索的方法
     */

    @Transactional(readOnly = true)
    public List<Book> getAllBookByAuthorId (@NonNull Long authorId) {
        Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("找不到這位作者")); 
        return bookRepository.findAllBooksByAuthorId(author.getId());
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthorName (@NonNull String authorName) {
        Author author = authorRepository.findByName(authorName)
                    .orElseThrow(() -> new RuntimeException("找不到這位作者"));
        return bookRepository.findAllBooksByAuthorId(author.getId());
    }

    //也許可以再簡化
    //1.把「找作者」與「找作者的所有書」拆成兩個方法，其中找作者可以各自寫，但共用的部分（查書）抽出來
    //2.讓 ID 與 Name 方法都呼叫一個「主要方法」，只負責接收 Author 物件
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByTagId (@NonNull Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("找不到這個標籤"));
        return bookRepository.findAllByBookTags_Tag_Id(tag.getId());
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBookByTagName (@NonNull String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                    .orElseThrow(() -> new RuntimeException("找不到這個標籤"));
        return bookRepository.findAllByBookTags_Tag_Id(tag.getId());
    }

    public List<Book> getAllBookByStatus (@NonNull String status) {
        return bookRepository.findAllBooksByStatus(status);
    }

    public List<Book> getAllBookByTitleKeyword (@NonNull String keyword) {
        return bookRepository.findAllByBookTitleContaining(keyword);
    }

    //複合條件查詢、考慮將上面那些加上其他條件的搜索方法去掉
    @Transactional(readOnly = true)
    public List<BookResponse> search(BookSearchCondition condition) {
        return bookRepository
            .search(condition)
            .stream()
            .map(bookMapper::toResponse)
            .toList();
    }

}
