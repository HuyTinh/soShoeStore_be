package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.model.Size;

import java.util.List;

public interface SizeService{
    List<Size> getAll();

    Size getById(Long id);
}
