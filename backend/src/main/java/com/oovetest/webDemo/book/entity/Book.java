package com.oovetest.webDemo.book.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.booktag.BookTag;
import com.oovetest.webDemo.experience.entity.Experience;
import com.oovetest.webDemo.series.entity.Series;
import com.oovetest.webDemo.tag.entity.Tag;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "book",
    uniqueConstraints = { // 確保同一系列同一集數只能有一筆紀錄
        @UniqueConstraint(columnNames = {"series_id", "volume"}),
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class Book {
    // 心得、平台

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "isbn", unique = true, nullable = true)
    private String isbn;

    @Column(name = "buy_time")
    private LocalDateTime buytime;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    //加入中介表，改成one-to-many + many-to-one
    @OneToMany(
        mappedBy = "book",
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<BookTag> bookTags = new HashSet<>();
    
    //追加關於系列的設定
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Column(nullable = true)
    private Series series;

    @Column(nullable = true)
    private Integer volume; // 第幾集

    //心得
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Experience experience;


    // Helper 方法

    // 加一個 Tag
    public void addTag(Tag tag) {
        BookTag.set(this, tag);
    }

    // 移除一個 Tag
    public void removeTag(Tag tag) {
        BookTag.remove(this, tag);
    }

    // 取得 Tag 集合（只讀）
    public Set<Tag> getTags() {
        return bookTags.stream()
                       .map(BookTag::getTag)
                       .collect(Collectors.toUnmodifiableSet());
    }
}

