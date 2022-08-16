package com.example.practice.controller;

import com.example.practice.dto.ArchivePossessionDto;
import com.example.practice.service.ArchivePossessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archive")
public class ArchivePossessionController {

    private final ArchivePossessionService archivePossessionService;

    @GetMapping
    public ResponseEntity<List<ArchivePossessionDto>> getAll(@RequestParam(defaultValue = "0", name = "page") int page,
                                                             @RequestParam(defaultValue = "20", name = "size") int size) {
        return ResponseEntity.ok(archivePossessionService.readAll(page, size));
    }

}
