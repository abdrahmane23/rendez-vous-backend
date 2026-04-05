package com.rendezvous.demo.Controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;


import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.service.SectionService;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public Section getSectionById(@PathVariable UUID id) {
        return sectionService.getSectionById(id); 
    }

    @PostMapping("/{id}")
    public Section updateSection(@PathVariable UUID id ,@RequestBody Section section) {
        return sectionService.updateSection(id, section);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable UUID id) {
        sectionService.deleteSection(id);
    }
        
}
