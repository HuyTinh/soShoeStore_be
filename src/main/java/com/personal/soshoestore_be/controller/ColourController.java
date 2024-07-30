package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.service.ColourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/colours")
public class ColourController {
    private final ColourService colourService;

    @GetMapping
    public ResponseEntity<?> getColours() {
        return ResponseEntity.ok(colourService.getAll());
    }
}
