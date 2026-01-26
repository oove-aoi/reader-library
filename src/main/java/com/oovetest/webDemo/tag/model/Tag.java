package com.oovetest.webDemo.tag.model;

import com.oovetest.webDemo.booktag.BookTag;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(
    name = "tag",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    @EqualsAndHashCode.Include
    private Long Id;

    @Column(name = "tag_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "tag_group_id", nullable = false)
    private TagGroup group;

    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<BookTag> bookTags = new HashSet<>();
    
}
