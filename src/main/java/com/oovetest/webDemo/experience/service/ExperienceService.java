package com.oovetest.webDemo.experience.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.experience.dto.ExperienceRequest;
import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.model.Experience;
import com.oovetest.webDemo.experience.mapper.ExperienceMapper;

import com.oovetest.webDemo.experience.repository.ExperienceRepository;

@Service
@Transactional
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final BookRepository bookRepository;
    private final ExperienceMapper experienceMapper;

    public ExperienceService(ExperienceRepository experienceRepository,
                            BookRepository bookRepository,
                            ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.bookRepository = bookRepository;
        this.experienceMapper = experienceMapper;
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceByBookTitle(String title) {
        Experience experience = experienceRepository.findByBook_BookTitle(title)
                .orElseThrow(() -> new RuntimeException("找不到這本書的心得"));

        return experienceMapper.toResponse(experience);
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceByBookId(Long bookId) {
        Experience experience =  experienceRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new RuntimeException("找不到這本書的心得"));
        return experienceMapper.toResponse(experience);
    }
    
    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long experienceId) {
        Experience experience =  experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("找不到這本書的心得"));

        return experienceMapper.toResponse(experience);
    }

    public ExperienceResponse saveExperience(ExperienceRequest experienceRequest) {
        Experience experience = new Experience();
        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);
        experience.setCreatedAt(LocalDateTime.now());

        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    public ExperienceResponse updateExperience(Long experienceId, 
                                               ExperienceRequest experienceRequest) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("找不到心得"));

        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);

        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}
