package com.example.practice.mapper;

import com.example.practice.dto.ArchivePossessionDto;
import com.example.practice.entity.ArchivePossession;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArchivePossessionMapper {

    ArchivePossession fromDto(ArchivePossessionDto archivePossessionDto);

    ArchivePossessionDto toDto(ArchivePossession archivePossession);

    List<ArchivePossession> fromDto(List<ArchivePossessionDto> archivePossessionDtoList);

    List<ArchivePossessionDto> toDto(List<ArchivePossession> archivePossessionList);
}
