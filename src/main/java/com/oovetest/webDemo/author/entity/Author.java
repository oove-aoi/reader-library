package com.oovetest.webDemo.author.entity;

import java.util.Set;

import com.oovetest.webDemo.book.entity.Book;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "author",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private Set<Book> books = new java.util.HashSet<>();


    //helper method 以維護雙邊關聯
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setAuthor(null);
    }    
}
