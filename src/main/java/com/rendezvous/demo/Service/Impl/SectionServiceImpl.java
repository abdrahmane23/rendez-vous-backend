package com.rendezvous.demo.service.Impl;

import com.rendezvous.demo.Exception.SectionNotFoundException;
import com.rendezvous.demo.Exception.TestNotFoundException;
import com.rendezvous.demo.Repositories.TestRepo;
import com.rendezvous.demo.service.SectionService;
import com.rendezvous.demo.domain.Dtos.CreateSectionRequestDto;
import com.rendezvous.demo.domain.Dtos.CreateTestRequestDto;
import com.rendezvous.demo.domain.Entities.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rendezvous.demo.Repositories.SectionRepo;
import com.rendezvous.demo.domain.Entities.Section;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    
    private final SectionRepo sectionRepo;
    private final TestRepo testRepo;


    public Page<Section> getAllSections(Pageable pageable) {
         return sectionRepo.findAll(pageable);
    }
    
    public Section getSectionById(UUID id) {
        return sectionRepo.findById(id)
        .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + id));
    }
    @Transactional
    public void createSection(CreateSectionRequestDto section) {
        Section newSection = new Section();
        newSection.setName(section.getName());
        newSection.setLocation(section.getLocation());
        newSection.setCapacity(section.getCapacity());
        sectionRepo.save(newSection);
        if (section.getTest()!= null) {
            Test test = new Test();
            CreateTestRequestDto testDto = section.getTest();
            test.setName(testDto.getName());
            test.setDuration(testDto.getDuration());
            test.setTimeOfResult(testDto.getTimeOfResult());
            test.setSection(newSection);
            Test saved = testRepo.saveAndFlush(test);
            System.out.println(saved.getId());
        }
    }

    @Transactional
    public void updateSection(UUID id ,CreateSectionRequestDto updatedSection) {
        Section existingSection = sectionRepo.findById(id)
            .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + id));

        existingSection.setName(updatedSection.getName());
        existingSection.setLocation(updatedSection.getLocation());
        existingSection.setCapacity(updatedSection.getCapacity());
        if (updatedSection.getTest() != null) {
            if (existingSection.getTest()!=null){
                Test existingTest = existingSection.getTest();
                CreateTestRequestDto testDto = updatedSection.getTest();
                existingTest.setName(testDto.getName());
                existingTest.setDuration(testDto.getDuration());
                existingTest.setTimeOfResult(testDto.getTimeOfResult());
            }else {
                Test test = new Test();
                CreateTestRequestDto testDto = updatedSection.getTest();
                test.setName(testDto.getName());
                test.setDuration(testDto.getDuration());
                test.setTimeOfResult(testDto.getTimeOfResult());
                test.setSection(existingSection);
                testRepo.save(test);
            }
            } else {
            Test existingTest = existingSection.getTest();
            if (existingTest != null) {
                existingTest.setSection(null);
                existingSection.setTest(null);
                testRepo.delete(existingTest);
            }
        }
    }
    public void deleteSection(UUID id) {
                Section existingSection = sectionRepo.findById(id)
        .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + id));

        sectionRepo.delete(existingSection);
    }

}
