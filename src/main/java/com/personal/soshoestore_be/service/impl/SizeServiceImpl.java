package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.model.Size;
import com.personal.soshoestore_be.repository.SizeRepository;
import com.personal.soshoestore_be.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    @Override
    @Cacheable(value = "sizes", key = "#root.method.name")
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getById(Long id) {
        return sizeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("Size with (id %d) not found", id))
        );
    }
}
