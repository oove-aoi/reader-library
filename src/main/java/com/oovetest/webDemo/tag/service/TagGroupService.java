package com.oovetest.webDemo.tag.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.tag.repository.TagGroupRepository;
import com.oovetest.webDemo.tag.dto.TagGroupRequest;
import com.oovetest.webDemo.tag.dto.TagGroupUpdateRequest;
import com.oovetest.webDemo.tag.dto.TagGroupWithTagsResponse;
import com.oovetest.webDemo.tag.model.TagGroup;
import com.oovetest.webDemo.tag.mapper.TagGroupMapper;


@Service
@Transactional
public class TagGroupService {
    private final TagGroupRepository tagGroupRepository;
    private final TagGroupMapper tagGroupMapper;

    public TagGroupService(TagGroupRepository tagGroupRepository, TagGroupMapper tagGroupMapper) {
        this.tagGroupRepository = tagGroupRepository;
        this.tagGroupMapper = tagGroupMapper;
    }

    public TagGroup findByName(String name) {
        return tagGroupRepository.findByTagGroupName(name)
                                 .orElseThrow(() -> new RuntimeException("TagGroup not found"));
    }


    public TagGroup findById(Long id) {
        return tagGroupRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("TagGroup not found"));
    }

    public TagGroup createTagGroup(TagGroupRequest request) {
        //code還是別用setter了、免得沒驗證出事
        TagGroup tagGroup = new TagGroup(request.getCode(), request.getDisplayName());
        
        return tagGroupRepository.save(tagGroup);
    }

    public TagGroup updateTagGroup(Long id, TagGroupUpdateRequest request) {
        TagGroup tagGroup = findById(id);             
        tagGroup.setTagGroupName(request.getDisplayName());

        return tagGroupRepository.save(tagGroup);
    }

    public TagGroupWithTagsResponse findTagsByGroupId(Long groupId) {
        TagGroup tagGroup = findById(groupId);

        return tagGroupMapper.toTagGroupWithTagsResponse(tagGroup);
    }

    //刪除方法不提供，有需要的話可使用軟刪除的形式處理
    
}