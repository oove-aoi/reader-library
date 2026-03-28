package com.oovetest.webDemo.experience.mapper;

import java.util.List;

import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.dto.ExperienceSimpleResponse;
import com.oovetest.webDemo.experience.entity.Experience;

import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {
    public ExperienceResponse toResponse(Experience experience) {
        ExperienceResponse response = new ExperienceResponse();

        response.setBookId(experience.getBook().getId());
        response.setBookTitle(experience.getBook().getBookTitle());

        response.setContent(experience.getContent());
        response.setRating(experience.getRating());
        response.setCreatedAt(experience.getCreatedAt());

        return response;
    }

    public List<ExperienceResponse> toResponse(List<Experience> experiences) {
        return experiences.stream()
                          .map(this::toResponse)
                          .toList();
    }

    public ExperienceSimpleResponse toSimpleResponse(Experience experience) {
        ExperienceSimpleResponse response = new ExperienceSimpleResponse();

        response.setId(experience.getId());
        response.setBookId(experience.getBook().getId());
        response.setBookTitle(experience.getBook().getBookTitle());
        response.setRating(experience.getRating());

        return response;
    }
    
    public List<ExperienceSimpleResponse> toSimpleResponse(List<Experience> experiences) {
        return experiences.stream()
                          .map(this::toSimpleResponse)
                          .toList();
    }
}
