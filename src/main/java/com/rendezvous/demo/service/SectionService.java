package com.rendezvous.demo.service;

import org.springframework.stereotype.Service;

import com.rendezvous.demo.Repositories.SectionRepository;
import com.rendezvous.demo.domain.Entities.Section;
import java.util.List;
import java.util.UUID;

@Service
public class SectionService {
    
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }
    
    public Section getSectionById(UUID id) {
        return sectionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));
    }

    public Section creatSection(Section section) {
        return sectionRepository.save(section);
    }

    public Section updateSection(UUID id ,Section updatedSection) {
        Section existingSection = sectionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));

        existingSection.setName(updatedSection.getName());
        existingSection.setLocation(updatedSection.getLocation());

        return sectionRepository.save(existingSection);
    }

    public void deleteSection(UUID id) {
                Section existingSection = sectionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));

        sectionRepository.delete(existingSection);
    }

}
