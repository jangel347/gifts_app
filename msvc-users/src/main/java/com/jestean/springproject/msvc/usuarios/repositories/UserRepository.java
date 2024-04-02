package com.jestean.springproject.msvc.usuarios.repositories;

import com.jestean.springproject.msvc.usuarios.models.entities.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
