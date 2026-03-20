package com.oovetest.webDemo.tag.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.oovetest.webDemo.tag.domain.TagGroupCode;

@Entity
@Table(
    name = "tag_group",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tag_group_name"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class TagGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_group_id")
    @EqualsAndHashCode.Include
    private Long groupid;

    //工程端、使用domain中enum定義的選項 GENRE, TONE, RELATIONSHIP 
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TagGroupCode code;   

    //實務上給使用者用的名稱
    @Column(nullable = false, unique = true, name = "tag_group_name")
    private String tagGroupName; 

    @OneToMany(
        mappedBy = "group",
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<Tag> tags = new HashSet<>();

    public TagGroup(TagGroupCode code, String tagGroupName) {
        //valueOf嘗試將傳入的code轉換為TagGroupCode enum，如果無效會拋出IllegalArgumentException
        this.code = TagGroupCode.valueOf(code.name()); 

        this.tagGroupName = tagGroupName;
    }
    
    //helper method 以維護雙邊關聯
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.setGroup(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setGroup(null);
    }

}
