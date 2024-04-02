package com.jestean.springproject.msvc.usuarios.services;

import com.jestean.springproject.msvc.usuarios.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listAll();
    Optional<User> getById(Long id);
    User save(User user);
    List<User> saveAll(List<User> users);
    void deleteById(Long id);

}
