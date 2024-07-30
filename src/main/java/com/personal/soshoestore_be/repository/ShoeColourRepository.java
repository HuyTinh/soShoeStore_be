package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.ShoeColour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeColourRepository extends JpaRepository<ShoeColour, Long> {
}
