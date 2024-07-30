package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.model.Colour;

import java.util.List;

public interface ColourService{
    List<Colour> getAll();

    Colour getById(Long id);
}
