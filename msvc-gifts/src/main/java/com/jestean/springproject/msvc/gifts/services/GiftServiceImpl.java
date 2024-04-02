package com.jestean.springproject.msvc.gifts.services;

import com.jestean.springproject.msvc.gifts.models.entities.Gift;
import com.jestean.springproject.msvc.gifts.repositories.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftServiceImpl implements GiftService{
    @Autowired
    private GiftRepository repository;
    @Override
    @Transactional(readOnly = true)
    public List<Gift> listAll() {
        return (List<Gift>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gift> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Gift save(Gift user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public List<Gift> saveAll(List<Gift> users) {
        return (List<Gift>) repository.saveAll(users);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
