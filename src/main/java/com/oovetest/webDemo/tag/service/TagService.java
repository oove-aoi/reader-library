package com.oovetest.webDemo.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.tag.dto.TagRequest;
import com.oovetest.webDemo.tag.model.Tag;
import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.repository.TagRepository;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final TagGroupService tagGroupService;

    public TagService(TagRepository tagRepository, TagGroupService tagGroupService) {
        this.tagRepository = tagRepository;
        this.tagGroupService = tagGroupService;
    }

    public Tag findByName(String name)  {
        return tagRepository.findByName(name)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));
    }

    public Tag findById(Long id)  {
        return tagRepository.findById(id)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));
    }

    public Tag createTag(TagRequest tagRequest) {
        Tag tag = new Tag();
        TagGroup group = tagGroupService.findById(tagRequest.getGroupId());

        tag.setName(tagRequest.getName());
        tag.setGroup(group);
        
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));
    
        TagGroup group = tagGroupService.findById(tagRequest.getGroupId());
    
        tag.setName(tagRequest.getName());
        tag.setGroup(group);
    
        return tagRepository.save(tag);
    }

    public Tag renameTag(Long tagId, String name) {
        Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));

        tag.setName(name);

        return tagRepository.save(tag);
    }

    public Tag moveTagToGroup(Long tagId, Long groupId) {
        Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));

        TagGroup group = tagGroupService.findById(groupId);
        tag.setGroup(group);

        return tagRepository.save(tag);
    }


    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

}
