package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.domain.Dtos.TestResultDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.TestResultResponseDto;
import com.rendezvous.demo.domain.Entities.TestResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestResultMapper {
    @Mapping(target = "examinerName", source = "testResult.appointement.examiner.name")
    TestResultResponseDto toTestResultResponseDto(TestResult testResult);

    @Mapping(target = "examinerName", source = "testResult.appointement.examiner.name")
    @Mapping(target = "appointementStart", source = "testResult.appointement.start")
    TestResultDetailsResponseDto toTestResultDetailsResponseDto(TestResult testResult);
}
