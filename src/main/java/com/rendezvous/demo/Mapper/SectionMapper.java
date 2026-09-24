package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.domain.Dtos.GetExaminerDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetSectionDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetSectionResponseDto;
import com.rendezvous.demo.domain.Dtos.GetTestResponseDto;
import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.domain.Entities.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface SectionMapper {

    @Mapping(target = "testName", source = "test.name")
    @Mapping(target = "duration", source = "test.duration")
    @Mapping(target = "capacity", source = "capacity")
    GetSectionResponseDto toDto(Section section );

    @Mapping(target = "capacity", source = "capacity")
    GetSectionDetailsResponseDto toGetSectionDetailsResponseDto(Section section);
    GetTestResponseDto toGetTestResponseDto(Test test);
}
