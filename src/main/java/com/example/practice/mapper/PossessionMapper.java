package com.example.practice.mapper;

import com.example.practice.dto.PossessionDto;
import com.example.practice.entity.Possession;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PossessionMapper {

    Possession fromDto(PossessionDto possessionDto);

    PossessionDto toDto(Possession possession);

    List<Possession> fromDto(List<PossessionDto> possessions);

    List<PossessionDto> toDto(List<Possession> possessions);


}
