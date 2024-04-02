package com.jestean.springproject.msvc.usuarios.services;

import com.jestean.springproject.msvc.usuarios.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listAll();
    Optional<User> listById(Long id);
    User save(User user);
    void deleteById(Long id);

}
