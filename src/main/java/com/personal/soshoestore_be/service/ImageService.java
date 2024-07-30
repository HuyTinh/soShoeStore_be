package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImagesByShoeId(Long shoeId);

    List<Image> saveAllImages(List<Image> images);
}
