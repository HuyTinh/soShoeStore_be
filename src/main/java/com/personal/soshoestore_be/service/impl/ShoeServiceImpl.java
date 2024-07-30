package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.dto.ShoeDTO;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.model.*;
import com.personal.soshoestore_be.repository.*;
import com.personal.soshoestore_be.response.ShoeCategoriesResponse;
import com.personal.soshoestore_be.service.CloudinaryService;
import com.personal.soshoestore_be.service.ShoeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;

    private final ColourRepository colourRepository;

    private final SizeRepository sizeRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    public Shoe getShoeById(Long id) {
        Shoe shoeFindById = shoeRepository.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("Shoe with (id = %d) is not found", id)));
        log.info(String.format("Finding shoe with id %d from database in shoes table.", id));
        return shoeFindById;
    }

    @Override
    public List<Shoe> getShoeByName(String name) {
        List<Shoe> findShoesByName = shoeRepository.findByName(name);
        log.info(String.format("Finding shoe with name %s from database in shoes table.", name));
        return findShoesByName;
    }

    @Override
    public Shoe createShoe(ShoeDTO shoeDTO, List<MultipartFile> imageThumbnail, List<MultipartFile> imageDetails){
        log.info("Starting to create shoe.");
        Shoe newShoe = Shoe.builder()
                .name(shoeDTO.getName())
                .price(shoeDTO.getPrice())
                .description(shoeDTO.getDescription())
                .build();
        log.info("Setting name, price, description, to shoe successfully.");
        ShoeColour shoeColour = ShoeColour.builder().shoe(newShoe)
                .vamp(colourRepository.findById(shoeDTO.getShoeColourDTO().getVamp()).orElseThrow(() -> new DataNotFoundException("Vamp not found with id: " + shoeDTO.getShoeColourDTO().getVamp())))
                .sole(colourRepository.findById(shoeDTO.getShoeColourDTO().getSole()).orElseThrow(() -> new DataNotFoundException("Sole not found with id: " + shoeDTO.getShoeColourDTO().getSole())))
                .quarter(colourRepository.findById(shoeDTO.getShoeColourDTO().getQuarter()).orElseThrow(() -> new DataNotFoundException("Quarter not found with id: " + shoeDTO.getShoeColourDTO().getQuarter()))).build();
        newShoe.setShoeColour(shoeColour);
        log.info("Setting shoeColour to shoe successfully.");
        newShoe.setSizes(new ArrayList<>(
                shoeDTO.getSizeId().stream().map(sizeId -> sizeRepository.findById(sizeId).orElseThrow(
                        () -> new DataNotFoundException(String.format("Size with (id = %d) is not found", sizeId))
                )).toList()
        ));
        log.info("Setting sizes to shoe successfully.");
        imageUploadValidation(newShoe, imageThumbnail, imageDetails);
        log.info("Setting images, imageUrl, imageUrlBack to shoe successfully.");
        Shoe savedShoe =  shoeRepository.save(newShoe);
        log.info("Save shoe to database successfully.");
        return savedShoe;
    }

    @Override
    public Shoe updateShoe(Long id, ShoeDTO shoeDTO, List<MultipartFile> imageThumbnail, List<MultipartFile> imageDetails)  {
        Optional<Shoe> optionalShoe = shoeRepository.findById(id);
    if(optionalShoe.isPresent()){
        log.info("Starting to update shoe.");
        Shoe existingShoe = optionalShoe.get();
        existingShoe.setName(shoeDTO.getName());
        existingShoe.setPrice(shoeDTO.getPrice());
        existingShoe.setDescription(shoeDTO.getDescription());
        existingShoe.setImageUrl(shoeDTO.getImageUrl());
        existingShoe.setImageUrlBack(shoeDTO.getImageUrlBack());

        log.info("Updating name, price, description, imageUrl, imageUrlBack to shoe successfully.");
        ShoeColour shoeColour = existingShoe.getShoeColour();
        shoeColour.setVamp(colourRepository.findById(shoeDTO.getShoeColourDTO().getVamp()).orElseThrow(() -> new DataNotFoundException("Vamp not found with id: " + shoeDTO.getShoeColourDTO().getVamp())));
        shoeColour.setSole(colourRepository.findById(shoeDTO.getShoeColourDTO().getSole()).orElseThrow(() -> new DataNotFoundException("Sole not found with id: " + shoeDTO.getShoeColourDTO().getSole())));
        shoeColour.setQuarter(colourRepository.findById(shoeDTO.getShoeColourDTO().getQuarter()).orElseThrow(() -> new DataNotFoundException("Quarter not found with id: " + shoeDTO.getShoeColourDTO().getQuarter())));
        existingShoe.setShoeColour(shoeColour);

        log.info("Updating shoeColour to shoe successfully.");
        existingShoe.setSizes(new ArrayList<>(
                shoeDTO.getSizeId().stream().map(sizeId -> sizeRepository.findById(sizeId).orElseThrow(
                        () -> new DataNotFoundException(String.format("Size with (id = %d) is not found", sizeId))
                )).toList()
        ));

        log.info("Updating sizes to shoe successfully.");
        existingShoe.getImages().removeIf(image ->
                shoeDTO.getImages().stream().noneMatch(
                    img -> Objects.equals(img.getId(), image.getId())));
        imageUploadValidation(existingShoe, imageThumbnail, imageDetails);

        log.info("Updating images, imageUrl, imageUrlBack to shoe successfully.");


        Shoe updatedShoe = shoeRepository.save(existingShoe);
        log.info(String.format("Updating shoe with id %d in shoes table.", id));
        return updatedShoe;
    }
        return null;
    }

    @Override
    public void deleteShoe(Long id) {
        Optional<Shoe> optionalShoe = shoeRepository.findById(id);
        optionalShoe.ifPresent(shoeRepository::delete);
        log.info(String.format("Deleting shoe with id %d in shoes table.", id));
    }

    @Override
    public Page<Shoe> getAllShoes(String name, Pageable pageable) {
        Page<Shoe> shoePage = shoeRepository.findByName(name, pageable);
        log.info("Fetching shoes by name with pagination page {} size {} from database in shoes table.", pageable.getPageNumber(), pageable.getPageSize());
        return shoePage;
    }

    @Override
    public List<YearSales> getShoeYearSales(int year, int shoeId) {
        return shoeRepository.findShoeYearSales(year, shoeId);
    }

    @Override
    public List<MonthSales> getShoeMonthSales(int year, int month, int shoeId) {
        return shoeRepository.findShoeMonthSales(year, month, shoeId);
    }

    @Override
    public List<Shoe> getCurrentShoesMustHave() {
        return shoeRepository.getCurrentShoesMustHave();
    }

    @Override
    public List<ShoeCategoriesResponse> getShoesCategories() {
        return shoeRepository.getShoesCategories().stream().map(
                object -> ShoeCategoriesResponse.builder().title(String.valueOf(object[0])).imageUrl(String.valueOf(object[1])).build()
        ).toList();
    }

    private void imageUploadValidation(Shoe shoe ,List<MultipartFile> imageThumbnail, List<MultipartFile> imageDetails){
    try {
        if (imageThumbnail != null && !imageThumbnail.isEmpty()) {
            List<String> imageUrls = imagesUpload(imageThumbnail);
            shoe.setImageUrl(imageUrls.get(0));
            shoe.setImageUrlBack(imageUrls.get(1));
        }
        if (imageDetails != null && !imageDetails.isEmpty()) {
            shoe.getImages().addAll(imagesUpload(imageDetails).stream().map(imageUrl -> Image.builder().imageUrl(imageUrl).shoe(shoe).build()).toList());
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}


    public List<String> imagesUpload(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : Objects.requireNonNullElse(files, new ArrayList<MultipartFile>())) {
            if(file.getSize() == 0) {continue;}
            if(file.getSize() > 10 * 1024 * 1024) { // 10MB
                throw new RuntimeException("File size must be less than 10MB.");
            }

            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new RuntimeException("File must be an image.");
            }
        }
        return cloudinaryService.uploadMultiFile(files);
    }
}