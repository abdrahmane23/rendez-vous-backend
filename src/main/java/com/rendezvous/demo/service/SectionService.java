package com.rendezvous.demo.service;

import com.rendezvous.demo.domain.Dtos.CreateSectionRequestDto;
import com.rendezvous.demo.domain.Entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SectionService {
    public Page<Section> getAllSections(Pageable pageable);
    public Section getSectionById(UUID id);
    public void createSection(CreateSectionRequestDto section);
    public void updateSection(UUID id ,CreateSectionRequestDto updatedSection);
    public void deleteSection(UUID id);

}
