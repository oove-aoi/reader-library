package com.oovetest.webDemo.experience.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.experience.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    public Optional<Experience> findByBook_BookTitle(String title);
    public Optional<Experience> findByBook_Id(Long bookId);
}
