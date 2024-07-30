package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.dto.ShoeDTO;
import com.personal.soshoestore_be.model.Shoe;
import com.personal.soshoestore_be.model.MonthSales;
import com.personal.soshoestore_be.model.YearSales;
import com.personal.soshoestore_be.response.ShoeCategoriesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShoeService{

    Shoe createShoe(ShoeDTO ShoeDTO, List<MultipartFile> imageThumbnail, List<MultipartFile> imageDetails);

    Shoe updateShoe(Long id, ShoeDTO ShoeDTO, List<MultipartFile> imageThumbnail, List<MultipartFile> imageDetails) ;

    void deleteShoe(Long id);

    Shoe getShoeById(Long id);

    List<Shoe> getShoeByName(String name);

    Page<Shoe> getAllShoes(String name, Pageable pageable);

    List<YearSales> getShoeYearSales(int year, int shoeId);

    List<MonthSales> getShoeMonthSales(int year, int month, int shoeId);

    List<Shoe> getCurrentShoesMustHave();

    List<ShoeCategoriesResponse> getShoesCategories();


}
