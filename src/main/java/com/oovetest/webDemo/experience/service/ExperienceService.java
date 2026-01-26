package com.oovetest.webDemo.experience.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.oovetest.webDemo.book.model.Book;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.experience.dto.ExperienceRequest;
import com.oovetest.webDemo.experience.model.Experience;


import com.oovetest.webDemo.experience.repository.ExperienceRepository;

@Service
@Transactional
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final BookRepository bookRepository;

    public ExperienceService(ExperienceRepository experienceRepository,
                            BookRepository bookRepository) {
        this.experienceRepository = experienceRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public Experience getExperienceByBookTitle(String title) {
        return experienceRepository.findByBook_BookTitle(title)
                .orElseThrow(() -> new RuntimeException("找不到這本書的心得"));
    }

    @Transactional(readOnly = true)
    public Experience getExperienceByBookId(Long bookId) {
        return experienceRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new RuntimeException("找不到這本書的心得"));
    }
    
    @Transactional(readOnly = true)
    public Experience getExperienceById(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("找不到心得"));
    }

    public Experience saveExperience(ExperienceRequest experienceRequest) {
        Experience experience = new Experience();
        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);
        experience.setCreatedAt(LocalDateTime.now());

        return experienceRepository.save(experience);
    }

    public Experience updateExperience(Long experienceId, 
                                       ExperienceRequest experienceRequest) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("找不到心得"));

        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);

        return experienceRepository.save(experience);
    }

    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}
