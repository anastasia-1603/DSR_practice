package com.example.practice.service;

import com.example.practice.dto.ArchivePossessionDto;
import com.example.practice.mapper.ArchivePossessionMapper;
import com.example.practice.repository.ArchivePossessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivePossessionService {

    private final ArchivePossessionRepository archivePossessionRepository;
    private final ArchivePossessionMapper archivePossessionMapper;

    public List<ArchivePossessionDto> readAll() {
        return archivePossessionMapper.toDto(archivePossessionRepository.findAll());
    }

}
