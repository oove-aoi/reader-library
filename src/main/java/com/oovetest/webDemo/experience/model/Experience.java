package com.oovetest.webDemo.experience.model;

import java.time.LocalDateTime;

import com.oovetest.webDemo.book.model.Book;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(
    name = "experience",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"book_id"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class Experience {
    //心得 
    //心得內容、評分
    
    @Id
    private Long id;

    @Lob //@Lob 會自動讓 JPA 使用大欄位（CLOB/TEXT），且跨資料庫移植性好
    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    @Min(0)
    @Max(10)
    private int rating; //考慮改Interger 以允許null

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @MapsId  // ← 這個關鍵字表示使用相同的主鍵值
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;
}
