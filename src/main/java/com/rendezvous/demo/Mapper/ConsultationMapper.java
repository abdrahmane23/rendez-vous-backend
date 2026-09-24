package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.Repositories.ConsultationRepo;
import com.rendezvous.demo.domain.Dtos.ConsultationDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.ConsultationResponseDto;
import com.rendezvous.demo.domain.Entities.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsultationMapper {

    @Mapping(target = "examinerName", source = "consultation.appointement.examiner.name")
    ConsultationResponseDto toConsultationResponseDto(Consultation consultation);

    @Mapping(target = "examinerName", source = "consultation.appointement.examiner.name")
    @Mapping(target = "appointementStart", source = "consultation.appointement.start")
    ConsultationDetailsResponseDto toConsultationDetailsResponseDto(Consultation consultation);
}
