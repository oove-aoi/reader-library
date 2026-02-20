package com.oovetest.webDemo.tag.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.tag.dto.TagRequest;
import com.oovetest.webDemo.tag.dto.TagResponse;
import com.oovetest.webDemo.tag.model.Tag;
import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.repository.TagRepository;
import com.oovetest.webDemo.tag.mapper.TagMapper;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final TagGroupService tagGroupService;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagGroupService tagGroupService, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagGroupService = tagGroupService;
        this.tagMapper = tagMapper;
    }

   
    public Tag getEntityById(Long id) {
        return tagRepository.findById(id)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));
    }

    public Tag getEntityByName(String name) {
        return tagRepository.findByName(name)
                            .orElseThrow(()-> new RuntimeException("找不到這個標籤"));
    }

    public TagResponse findByName(String name)  {
        Tag tag = getEntityByName(name);
        return tagMapper.toResponse(tag);
    }

    public TagResponse findById(Long id)  {
        Tag tag = getEntityById(id);
        return tagMapper.toResponse(tag);
    }

    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = new Tag();
        TagGroup group = tagGroupService.getEntityById(tagRequest.getGroupId());

        tag.setName(tagRequest.getName());
        tag.setGroup(group);
        
        return tagMapper.toResponse(tagRepository.save(tag));
    }

    public TagResponse updateTag(Long tagId, TagRequest tagRequest) {
        Tag tag = getEntityById(tagId);
    
        TagGroup group = tagGroupService.getEntityById(tagRequest.getGroupId());
    
        tag.setName(tagRequest.getName());
        tag.setGroup(group);
    
        return tagMapper.toResponse(tagRepository.save(tag));
    }

    public TagResponse renameTag(Long tagId, String name) {
        Tag tag = getEntityById(tagId);

        tag.setName(name);

        return tagMapper.toResponse(tagRepository.save(tag));
    }

    public TagResponse moveTagToGroup(Long tagId, Long groupId) {
        Tag tag = getEntityById(tagId);
        TagGroup group = tagGroupService.getEntityById(groupId);
        tag.setGroup(group);

        return tagMapper.toResponse(tagRepository.save(tag));
    }


    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

}
