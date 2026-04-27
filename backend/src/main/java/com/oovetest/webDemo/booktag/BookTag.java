package com.oovetest.webDemo.booktag;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.tag.entity.Tag;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//預定 book跟tags兩者多對多關係的中介表
@Entity
@Table(
    name = "book_tags",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"book_id", "tag_id"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //說是實體別用@data等lombok方法
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    //中介表的工廠方法
    public static BookTag set(Book book, Tag tag) {
        BookTag bt = new BookTag();
        bt.setBook(book);
        bt.setTag(tag);

        book.getBookTags().add(bt);
        tag.getBookTags().add(bt);
        
        return bt;
    }
    //中介表的移除方法
    public static void remove(Book book, Tag tag) {
        book.getBookTags().removeIf(bt -> bt.getTag().equals(tag));
        tag.getBookTags().removeIf(bt -> bt.getBook().equals(book));
    }
}
