package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.model.Image;
import com.personal.soshoestore_be.repository.ImageRepository;
import com.personal.soshoestore_be.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public List<Image> getImagesByShoeId(Long shoeId) {
        return imageRepository.findByShoeId(shoeId);
    }

    @Override
    public List<Image> saveAllImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }
}
