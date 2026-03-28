package com.oovetest.webDemo.experience.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.oovetest.webDemo.book.entity.Book;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.experience.dto.ExperienceRequest;
import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.entity.Experience;
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

    Experience getEntityById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("使用這個心得ID找不到這本書的心得"));
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long experienceId) {
        Experience experience =  getEntityById(experienceId);
        return experienceMapper.toResponse(experience);
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceByBookTitle(String title) {
        Experience experience = experienceRepository.findByBook_BookTitle(title)
                .orElseThrow(() -> new NotFoundException("使用這個書籍名稱找不到這本書的心得"));

        return experienceMapper.toResponse(experience);
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceByBookId(Long bookId) {
        Experience experience =  experienceRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new NotFoundException("使用此書籍ID找不到這本書的心得"));
        return experienceMapper.toResponse(experience);
    }

    public ExperienceResponse saveExperience(ExperienceRequest experienceRequest) {
        Experience experience = new Experience();
        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new NotFoundException("使用此書籍ID找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);
        experience.setCreatedAt(LocalDateTime.now());

        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    public ExperienceResponse updateExperience(Long experienceId, 
                                               ExperienceRequest experienceRequest) {
        Experience experience = getEntityById(experienceId);

        Book book = bookRepository.findById(experienceRequest.getBookId())
                .orElseThrow(() -> new NotFoundException("使用此書籍ID找不到這本書"));

        experience.setContent(experienceRequest.getContent());
        experience.setRating(experienceRequest.getRating());
        experience.setBook(book);

        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}
