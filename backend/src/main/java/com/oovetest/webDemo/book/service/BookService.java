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

        // ========= 基本資料 =========
        Author author = authorService.getEntityByName(bookRequest.authorName());

        String isbn = bookRequest.isbn();
        String seriesName = bookRequest.seriesName();
        Integer volume = bookRequest.volume();

        // ========= ISBN 處理 =========
        if (isbn == null || isbn.isBlank()) {
            isbn = null;
        } else {
            if (bookRepository.existsByIsbn(isbn)) {
                throw new IllegalStateException("已存在相同ISBN的書籍");
            }
        }

        // ========= 系列 / 集數 處理 =========
        Series series = null;

        if (seriesName != null && !seriesName.isBlank()) {
            // 有系列 → 取得 series
            series = seriesService.getEntityByTitle(seriesName);

            // 檢查 volume
            if (volume == null || volume < 1) {
                throw new IllegalArgumentException("系列書必須有有效的集數");
            }

            // 檢查唯一性
            if (bookRepository.existsBySeriesAndVolume(series, volume)) {
                throw new IllegalStateException("已存在相同系列與卷數的書籍");
            }

        } else {
            // 無系列 → volume 一律視為 null
            series = null;
            volume = null;
        }

        // ========= 設定 Entity（統一在這裡） =========
        book.setAuthor(author);
        book.setSeries(series);
        book.setVolume(volume);
        book.setIsbn(isbn);
        book.setBookTitle(bookRequest.title());
        book.setStatus(bookRequest.status());
        book.setBuytime(LocalDateTime.now());

        // ========= Tag 關聯 =========
        for (String tagName : bookRequest.tagNames()) {
            Tag tag = tagService.getEntityByName(tagName);
            book.addTag(tag);
        }

        // ========= 雙邊關聯 =========
        author.addBook(book);

        // ========= 儲存 =========
        return bookMapper.toResponse(bookRepository.save(book));
    }

    public BookResponse updateBookById(long bookId, BookRequest bookRequest) {

        Book book = getEntityById(bookId);

        // ========= Author 處理 =========
        Author newAuthor = authorService.getEntityByName(bookRequest.authorName());
        Author oldAuthor = book.getAuthor();

        if (!oldAuthor.equals(newAuthor)) {
            oldAuthor.getBooks().remove(book);
            newAuthor.addBook(book);
        }

        // ========= 基本資料 =========
        String isbn = bookRequest.isbn();
        String seriesName = bookRequest.seriesName();
        Integer volume = bookRequest.volume();

        // ========= ISBN =========
        if (isbn == null || isbn.isBlank()) {
            isbn = null;
        } else {
            boolean exists = bookRepository.existsByIsbn(isbn);
            if (exists && !isbn.equals(book.getIsbn())) {
                throw new IllegalStateException("已存在相同ISBN的書籍");
            }
        }

        // ========= Series / Volume =========
        Series series = null;

        if (seriesName != null && !seriesName.isBlank()) {
            series = seriesService.getEntityByTitle(seriesName);

            if (volume == null || volume < 1) {
                throw new IllegalArgumentException("系列書必須有有效的集數");
            }

            boolean exists = bookRepository.existsBySeriesAndVolume(series, volume);

            boolean isSameRecord =
                book.getSeries() != null &&
                book.getSeries().equals(series) &&
                book.getVolume() != null &&
                book.getVolume().equals(volume);

            if (exists && !isSameRecord) {
                throw new IllegalStateException("已存在相同系列與卷數的書籍");
            }

        } else {
            series = null;
            volume = null;
        }

        // ========= Tag（先清再加） =========
        book.getTags().clear();

        for (String tagName : bookRequest.tagNames()) {
            Tag tag = tagService.getEntityByName(tagName);
            book.addTag(tag);
        }

        // ========= 統一設定 =========
        book.setBookTitle(bookRequest.title());
        book.setStatus(bookRequest.status());
        book.setIsbn(isbn);
        book.setSeries(series);
        book.setVolume(volume);

        return bookMapper.toResponse(bookRepository.save(book));
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
