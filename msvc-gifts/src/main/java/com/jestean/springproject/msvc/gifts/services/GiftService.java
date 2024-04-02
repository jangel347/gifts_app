package com.jestean.springproject.msvc.gifts.services;

import com.jestean.springproject.msvc.gifts.models.entities.Gift;

import java.util.List;
import java.util.Optional;

public interface GiftService {
    List<Gift> listAll();
    Optional<Gift> getById(Long id);
    Gift save(Gift user);
    List<Gift> saveAll(List<Gift> users);
    void deleteById(Long id);
}
