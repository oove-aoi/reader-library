package com.oovetest.webDemo.series.entity;

import com.oovetest.webDemo.author.entity.Author;
import com.oovetest.webDemo.book.entity.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
    name = "series",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class Series {
    
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private SeriesStatus status; // e.g., "ongoing", "completed"
    
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "series")
    private List<Book> books;

    public Series(SeriesStatus status, String title, Author author) {
        this.status = status;
        this.title = title;
        this.author = author;
    }

    // Helper 方法

    public void addBook(Book book) {
        books.add(book);
        book.setSeries(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setSeries(null);
    }

    public Set<Book> getBooks() {
        return books.stream().collect(Collectors.toSet());
    }


}
