package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerResponseDto;
import com.rendezvous.demo.domain.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    public User toEntity(CreateExaminerRequestDto user);
    public GetExaminerResponseDto toDto(User user);
    public GetExaminerDetailsResponseDto toDetailsDto(User user);

}
