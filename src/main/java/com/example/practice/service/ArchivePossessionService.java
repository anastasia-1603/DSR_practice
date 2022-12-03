package com.example.practice.service;

import com.example.practice.dto.ArchivePossessionDto;
import com.example.practice.mapper.ArchivePossessionMapper;
import com.example.practice.repository.ArchivePossessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivePossessionService {

    private final ArchivePossessionRepository archivePossessionRepository;
    private final ArchivePossessionMapper archivePossessionMapper;

    public List<ArchivePossessionDto> readAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return archivePossessionMapper.toDto(archivePossessionRepository.findAll(pageable).stream().toList());
    }

    public Page<ArchivePossessionDto> readAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return archivePossessionRepository.findAll(pageable)
                .map(archivePossessionMapper::toDto);
    }
}
