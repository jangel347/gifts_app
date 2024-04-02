package com.jestean.springproject.msvc.gifts.repositories;

import com.jestean.springproject.msvc.gifts.models.entities.Gift;
import org.springframework.data.repository.CrudRepository;

public interface GiftRepository extends CrudRepository<Gift, Long> {
    
}
