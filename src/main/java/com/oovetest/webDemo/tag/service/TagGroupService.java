package com.oovetest.webDemo.tag.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oovetest.webDemo.tag.repository.TagGroupRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.tag.dto.TagGroupResponse;
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

    @Transactional(readOnly = true)
    TagGroup getEntityById(Long id) {
        return tagGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID 未查找到相應的TagGroup"));
    }


    @Transactional(readOnly = true)
    public TagGroupResponse findByName(String name) {
        TagGroup tagGroup = tagGroupRepository.findByTagGroupName(name)
                .orElseThrow(() -> new NotFoundException("名稱未查找到相應的TagGroup"));
        return tagGroupMapper.toResponse(tagGroup);
    }

    @Transactional(readOnly = true)
    public TagGroupResponse findById(Long id) {
        TagGroup tagGroup = getEntityById(id);
        return tagGroupMapper.toResponse(tagGroup);
    }

    @Transactional(readOnly = true)
    public TagGroupWithTagsResponse findTagsByGroupId(Long groupId) {
        TagGroup tagGroup = getEntityById(groupId);
        return tagGroupMapper.toTagGroupWithTagsResponse(tagGroup);
    }

    @Transactional(readOnly = true)
    public List<TagGroupResponse> findAllTagGroups(){
        return tagGroupMapper.toResponse(tagGroupRepository.findAll());
    }

    //未來僅保留查詢方法，其他的創建、更新、刪除方法將不再提供
    /*
    public TagGroupResponse createTagGroup(TagGroupRequest request) {
        //code還是別用setter了、免得沒驗證出事
        TagGroup tagGroup = new TagGroup(request.getCode(), request.getDisplayName());
        
        return tagGroupMapper.toResponse(tagGroupRepository.save(tagGroup));
    }

    public TagGroupResponse updateTagGroup(Long id, TagGroupUpdateRequest request) {
        TagGroup tagGroup = getEntityById(id);                                             
        tagGroup.setTagGroupName(request.getDisplayName());

        return tagGroupMapper.toResponse(tagGroupRepository.save(tagGroup));
    }

    
    
    */
}