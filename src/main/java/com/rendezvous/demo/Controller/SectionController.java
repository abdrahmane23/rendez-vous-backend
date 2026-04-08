package com.rendezvous.demo.Controller;

import java.util.UUID;

import com.rendezvous.demo.Mapper.SectionMapper;
import com.rendezvous.demo.Service.SectionService;
import com.rendezvous.demo.domain.Dtos.CreateSectionRequestDto;
import com.rendezvous.demo.domain.Dtos.GetSectionDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetSectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.Service.Impl.SectionServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;
    private final SectionMapper sectionMapper;

    @GetMapping
    public ResponseEntity<Page<GetSectionResponseDto>> getAllSections(Pageable pageable) {
         Page <Section> sections=  sectionService.getAllSections(pageable);
        return ResponseEntity.ok(
                sections.map(sectionMapper::toDto));
    }

    @GetMapping("/{id}")
    public GetSectionDetailsResponseDto getSectionById(@PathVariable UUID id) {
        Section section = sectionService.getSectionById(id);
        return sectionMapper.toGetSectionDetailsResponseDto(section);
    }
    @PostMapping
    public ResponseEntity<Void> createSection(@RequestBody CreateSectionRequestDto section) {
        sectionService.createSection(section);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSection(@PathVariable UUID id ,@RequestBody CreateSectionRequestDto section) {
        sectionService.updateSection(id, section);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable UUID id) {
        sectionService.deleteSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
        
}
