package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>{
}
