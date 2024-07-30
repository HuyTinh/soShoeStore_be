package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Long>{
}
