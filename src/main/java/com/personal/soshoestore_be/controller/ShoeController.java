package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.ShoeDTO;
import com.personal.soshoestore_be.mapper.ShoeMapper;
import com.personal.soshoestore_be.model.Shoe;
import com.personal.soshoestore_be.response.ShoeResponse;
import com.personal.soshoestore_be.service.ShoeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("${api.prefix}/shoes")
@RequiredArgsConstructor
public class ShoeController {
    private final ShoeMapper shoeMapper;

    private final ShoeService shoeService;

    @GetMapping
    public ResponseEntity<?> getShoes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "search", defaultValue = "") String search)
    {
       return ResponseEntity.ok(shoeMapper.toListResponse(shoeService.getAllShoes(search, getPageable(page, size, sortBy))
                .map(shoeMapper::toResponse).map(shoeResponse -> {
                    shoeResponse.setShoeDifferentColour(shoeService.getShoeByName(shoeResponse.getName()).stream().map(shoeMapper::toShoeDifferentColourResponse).toList());
                    return shoeResponse;
                })));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShoeById(@PathVariable("id") long id)
    {
        Shoe existingShoe = shoeService.getShoeById(id);
        ShoeResponse shoeResponse = shoeMapper.toResponse(existingShoe);
         shoeResponse.setShoeDifferentColour(shoeService.getShoeByName(existingShoe.getName()).stream().map(shoeMapper::toShoeDifferentColourResponse).toList());
        return ResponseEntity.ok(shoeResponse);
    }

    @PostMapping
    public ResponseEntity<?> createShoe(
            @Valid @RequestPart("shoe") ShoeDTO shoeDTO,
            @RequestPart(value = "imageUrl", required = false) List<MultipartFile> imageUrl,
            @RequestPart(value = "imageDetailUrl", required = false) List<MultipartFile> imageDetailUrl)
    {
           return  ResponseEntity.ok(shoeMapper.toResponse(shoeService.createShoe(shoeDTO, imageUrl, imageDetailUrl)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShoe(@PathVariable("id") long id)
    {
        try {
            shoeService.deleteShoe(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShoe(
            @PathVariable("id") long id,
            @Valid @RequestPart("shoe") ShoeDTO shoeDTO,
            @RequestPart(value = "imageUrl", required = false) List<MultipartFile> imageUrl,
            @RequestPart(value = "imageDetailUrl", required = false) List<MultipartFile> imageDetailUrl)
    {
        return ResponseEntity.ok(shoeMapper.toResponse(shoeService.updateShoe(id, shoeDTO, imageUrl, imageDetailUrl)));
    }

    private Pageable getPageable(int page, int size, String sortBy) {
        Pageable pageable;
        if(!sortBy.isEmpty()){
            Sort sort = Sort.by(Sort.Direction.DESC);
            if(sortBy.contains("price")){
                if(sortBy.endsWith("high")){
                    sort = Sort.by(Sort.Direction.ASC, "price");
                } else if (sortBy.endsWith("low")){
                    sort = Sort.by(Sort.Direction.DESC, "price");
                }
            } else if (sortBy.contains("name")) {
                if(sortBy.endsWith("z")){
                    sort = Sort.by(Sort.Direction.DESC, "name");
                } else if (sortBy.endsWith("a")){
                    sort = Sort.by(Sort.Direction.ASC, "name");
                }
            }
            pageable =  PageRequest.of(page, size, sort);
        } else {
            pageable =  PageRequest.of(page, size);
        }
        return pageable;
    }

    @GetMapping("/year-sales")
    public ResponseEntity<?> getShoeYearSales(@RequestParam("shoeId") int shoeId,
                                              @RequestParam("year") int year)
    {
        return ResponseEntity.ok(shoeService.getShoeYearSales(year, shoeId));
    }

    @GetMapping("/month-sales")
    public ResponseEntity<?> getShoeMonthSales(@RequestParam("shoeId") int shoeId,
                                               @RequestParam("month") int month,
                                                @RequestParam(value = "year", defaultValue = "2024", required = false) int year)
    {
        return ResponseEntity.ok(shoeService.getShoeMonthSales(year, month,shoeId));
    }

    @GetMapping("/current-all-shoe-must-have")
    public ResponseEntity<?> getCurrentShoesMustHave(){
        return ResponseEntity.ok(shoeService.getCurrentShoesMustHave()
                .stream().map(shoeMapper::toResponse)
                .peek(shoeResponse -> shoeResponse.setShoeDifferentColour(shoeService.getShoeByName(shoeResponse.getName()).stream().map(shoeMapper::toShoeDifferentColourResponse).toList())).collect(Collectors.toList()));
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getShoesCategories(){
        return ResponseEntity.ok(shoeService.getShoesCategories());
    }

    //    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadImages(
//            @PathVariable("id") long id,
//            @ModelAttribute("files") List<MultipartFile> files)
//    {
//        try {
//            Shoe existingShoe = shoeService.getShoeById(id);
//            for (MultipartFile file : Objects.requireNonNullElse(files, new ArrayList<MultipartFile>())) {
//                if(file.getSize() == 0) {continue;}
//                if(file.getSize() > 10 * 1024 * 1024) { // 10MB
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size must be less than 10MB.");
//                }
//
//                String contentType = file.getContentType();
//                if(contentType == null || !contentType.startsWith("image/")){
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image.");
//                }
//            }
//            List<Image> shoeDetailImages = imageService.saveAllImages(cloudinaryService.uploadMultiFile(files).stream().map(imageUrl -> Image.builder().imageUrl(imageUrl).shoe(existingShoe).build()).toList());
//            return ResponseEntity.ok("Images uploaded successfully.");
//       } catch (Exception e) {
//            log.info("Error: ", e);
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

}
