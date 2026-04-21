package com.oovetest.webDemo.experience.mapper;

import java.util.List;

import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.dto.ExperienceSimpleResponse;
import com.oovetest.webDemo.experience.entity.Experience;

import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {
    public ExperienceResponse toResponse(Experience experience) {
        return new ExperienceResponse(
            experience.getBook().getId(),
            experience.getBook().getBookTitle(),
            experience.getContent(),
            experience.getRating(),
            experience.getCreatedAt()
        );
    }

    public List<ExperienceResponse> toResponse(List<Experience> experiences) {
        return experiences.stream()
                          .map(this::toResponse)
                          .toList();
    }

    public ExperienceSimpleResponse toSimpleResponse(Experience experience) {
        return new ExperienceSimpleResponse(
            experience.getId(),
            experience.getRating(),
            experience.getBook().getId(),
            experience.getBook().getBookTitle()
        );
    }
    
    public List<ExperienceSimpleResponse> toSimpleResponse(List<Experience> experiences) {
        return experiences.stream()
                          .map(this::toSimpleResponse)
                          .toList();
    }
}
