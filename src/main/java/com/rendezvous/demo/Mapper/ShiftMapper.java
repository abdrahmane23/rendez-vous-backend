package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.domain.Dtos.*;
import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ShiftMapper {
    @Mapping(target = "sectionName", source = "shift.section.name")
    GetShiftsForExaminerResponseDto toGetShiftsForExaminerResponseDto(Shift shift);
    @Mapping(target = "examinerName", source = "shift.examiner.name")
    GetShiftsForSectionResponseDto toGetShiftsForSectionResponseDto(Shift shift);

    GetShiftsDetailsResponseDto toGetShiftsDetailsResponseDto(Shift shift);
    GetExaminerForShiftResponseDto toGetExaminerForShiftResponseDto(User examiner);
    GetSectionForShiftResponseDto toGetSectionForShiftResponseDto(Section section);
}
