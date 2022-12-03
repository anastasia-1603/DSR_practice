package com.example.practice.controller;

import com.example.practice.dto.ArchivePossessionDto;
import com.example.practice.service.ArchivePossessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebArchiveController {

    public final ArchivePossessionService archiveService;

    @GetMapping("/web/archive/")
    public String getAll(Model model,
                         @RequestParam(defaultValue = "0", name = "page") int page,
                         @RequestParam(defaultValue = "20", name = "size") int size) {

        Page<ArchivePossessionDto> archive = archiveService.readAllPaginated(page, size);
        model.addAttribute("archive", archive);
        model.addAttribute("url", "/web/archive");
        return "archive";
    }

}
