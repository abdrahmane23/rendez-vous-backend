package com.rendezvous.demo.Mapper;

import com.rendezvous.demo.domain.Dtos.GetAppointementResponseDto;
import com.rendezvous.demo.domain.Entities.Appointement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointementMapper {
    @Mapping(target = "patientId", source = "appointement.patient.id")
    GetAppointementResponseDto toGetAppointementResponseDto(Appointement appointement);
}
