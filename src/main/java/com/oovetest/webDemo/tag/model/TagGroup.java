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
        @UniqueConstraint(columnNames = {"tagGroupName"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class TagGroup {
    @Id
    @GeneratedValue
    @Column(name = "tag_group_id")
    @EqualsAndHashCode.Include
    private Long groupid;

    //工程端、使用domain中enum定義的選項 GENRE, TONE, RELATIONSHIP 
    @Column(unique = true, nullable = false)
    private String code;   

    //實務上給使用者用的名稱
    @Column(nullable = false, unique = true, name = "display_name")
    private String tagGroupName; 

    @OneToMany(
        mappedBy = "group",
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<Tag> tags = new HashSet<>();

    public TagGroup(String code, String tagGroupName) {
        this.code = TagGroupCode.from(code).name();
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
