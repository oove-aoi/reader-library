package com.oovetest.webDemo.book.service;

import com.oovetest.webDemo.book.dto.BookRequest;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.SeriesBookSimpleResponse;
import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.author.service.AuthorService;
import com.oovetest.webDemo.tag.entity.Tag;
import com.oovetest.webDemo.tag.service.TagService;
import com.oovetest.webDemo.book.mapper.BookMapper;
import com.oovetest.webDemo.series.entity.Series;
import com.oovetest.webDemo.series.service.SeriesService;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    private final AuthorService authorService; ;
    private final TagService tagService;
    private final SeriesService seriesService;
    private final BookMapper bookMapper;


    //考慮未來bookService的重構，改成使用其他必要模組的service而非Repository
    public BookService(BookRepository bookRepository, 
            AuthorService authorService,
            TagService tagService,
            SeriesService seriesService,
            BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.tagService = tagService;
        this.bookMapper = bookMapper;
        this.seriesService = seriesService;
    }

    @Transactional(readOnly = true)
    private Book getEntityById(@NonNull Long bookId) {
        //findById 回傳 Optional<Book> 所以後面必須加上orElseThrow
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("ID 未查找到相應的書籍"));
    }

    @Transactional(readOnly = true)
    private Book getEntityByTitle(String bookName) {
        return bookRepository.findByBookTitle(bookName)
                .orElseThrow(() -> new NotFoundException("書名未查找到相應的書籍"));
    }


    @Transactional(readOnly = true)
    public BookResponse getBookById(long bookId) {
        return bookMapper.toResponse(getEntityById(bookId));
    }

    @Transactional(readOnly = true)
    public BookResponse getBookByTitle(String bookName) {
        return bookMapper.toResponse(getEntityByTitle(bookName));
    }

    public BookResponse createBook(BookRequest bookRequest) {
        //雙邊關係，關聯欄位得自己手動更新
        Book book = new Book();
        Author author = authorService.getEntityByName(bookRequest.authorName());
        Series series = seriesService.getEntityByTitle(bookRequest.seriesName());
        
        if(bookRequest.isbn() != null && bookRepository.existsByIsbn(bookRequest.isbn())) {
            throw new IllegalStateException("已存在相同ISBN的書籍");
        }
        if(bookRequest.seriesName() != null && bookRepository.existsBySeriesAndVolume(series, bookRequest.volume())) {
            throw new IllegalStateException("已存在相同系列與卷數的書籍");
        }
        if (bookRequest.isbn() != null && bookRequest.isbn().isBlank()) {
            book.setIsbn(null);
        };
        if (bookRequest.seriesName() != null && bookRequest.seriesName().isBlank()) {
            book.setSeries(null);
        };
        if (bookRequest.volume() != null && bookRequest.volume() < 1) {
            book.setVolume(null);
        };

        book.setAuthor(author);
        book.setSeries(series);
        book.setVolume(bookRequest.volume());
        book.setIsbn(bookRequest.isbn());
        book.setBookTitle(bookRequest.title());
        book.setStatus(bookRequest.status());
        book.setBuytime(LocalDateTime.now());

        for (String tagName : bookRequest.tagNames()) {
            Tag tag = tagService.getEntityByName(tagName);
            book.addTag(tag);  // helper method 自動生成 BookTag
        }

        author.addBook(book); 
        // 儲存到資料庫
        return bookMapper.toResponse(bookRepository.save(book));
    }

    public BookResponse updateBookById(long bookId, BookRequest bookRequest) {
        Book existingBook = getEntityById(bookId);
        Author author = authorService.getEntityByName(bookRequest.authorName());

        author.addBook(existingBook);    

        existingBook.setBookTitle(bookRequest.title());
        existingBook.setStatus(bookRequest.status());
        for (String tagName : bookRequest.tagNames()) {
            Tag tag = tagService.getEntityByName(tagName);
            existingBook.addTag(tag);  // helper method 自動生成 BookTag
        }

        return bookMapper.toResponse(bookRepository.save(existingBook));
    }

    public void deleteBookById(@NonNull Long bookId) {
        Book deleteBook = getEntityById(bookId);
        Author author = deleteBook.getAuthor();

        author.removeBook(deleteBook);
        bookRepository.deleteById(bookId);
    }


    public long countBySeriesId(Long seriesId) {
        return bookRepository.countBySeriesId(seriesId);
    }

    /*
    使用作者名、ID搜索書籍列表的方法、
    使用書籍名稱、閱讀狀況、書籍標籤搜索的方法
     */

    @Transactional(readOnly = true)
    public List<SeriesBookSimpleResponse> getAllBookByAuthorId (@NonNull Long authorId) {
        Author author = authorService.getEntityById(authorId);

        return bookRepository.findAllBooksByAuthorId(author.getId())
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SeriesBookSimpleResponse> getAllBooksByAuthorName (@NonNull String authorName) {
        Author author = authorService.getEntityByName(authorName);
        return bookRepository.findAllBooksByAuthorId(author.getId())
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    //也許可以再簡化
    //1.把「找作者」與「找作者的所有書」拆成兩個方法，其中找作者可以各自寫，但共用的部分（查書）抽出來
    //2.讓 ID 與 Name 方法都呼叫一個「主要方法」，只負責接收 Author 物件
    @Transactional(readOnly = true)
    public List<SeriesBookSimpleResponse> getAllBooksByTagId (@NonNull Long tagId) {
        Tag tag = tagService.getEntityById(tagId);
        return bookRepository.findAllByBookTags_Tag_Id(tag.getId())
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SeriesBookSimpleResponse> getAllBookByTagName (@NonNull String tagName) {
        Tag tag = tagService.getEntityByName(tagName);
        return bookRepository.findAllByBookTags_Tag_Id(tag.getId())
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    public List<SeriesBookSimpleResponse> getAllBookByStatus (@NonNull String status) {
        return bookRepository.findAllBooksByStatus(status)
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    public List<SeriesBookSimpleResponse> getAllBookByTitleKeyword (@NonNull String keyword) {
        return bookRepository.findAllByBookTitleContaining(keyword)
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    public List<SeriesBookSimpleResponse> getAllBooksInSeriesById(@NonNull Long seriesId) {
        return bookRepository.findAllBySeries_Id(seriesId)
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }

    /*
    public List<SeriesBookSimpleResponse> getAllBooksInSeriesByName(@NonNull String keyword) {
        return bookRepository.findAllBySeries_Name(keyword)
                .stream()
                .map(bookMapper::toSeriesBookSimpleResponse)
                .toList();
    }
    */

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
