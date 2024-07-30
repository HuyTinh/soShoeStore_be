package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.model.Colour;
import com.personal.soshoestore_be.repository.ColourRepository;
import com.personal.soshoestore_be.service.ColourService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColourServiceImpl implements ColourService {
    private final ColourRepository colourRepository;

    @Override
    @Cacheable(value = "colours", key = "#root.method.name")
    public List<Colour> getAll() {
        return colourRepository.findAll();
    }

    @Override
    public Colour getById(Long id) {
        return colourRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("Colour with (id %d) not found", id))
        );
    }
}
