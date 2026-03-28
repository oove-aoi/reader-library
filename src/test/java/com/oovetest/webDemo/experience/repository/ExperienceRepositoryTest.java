package com.oovetest.webDemo.experience.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.experience.entity.Experience;

public interface ExperienceRepositoryTest extends JpaRepository<Experience, Long> {
    
}
