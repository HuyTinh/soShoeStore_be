package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByShoeId(Long shoeId);
}
