package com.jestean.springproject.msvc.usuarios.controllers;

import com.jestean.springproject.msvc.usuarios.models.entities.User;
import com.jestean.springproject.msvc.usuarios.services.UserService;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/listAll")
    public List<User> listAll(){
        return service.listAll();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()) return ResponseEntity.ok(userOptional.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user){
        return service.save(user);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()){
            User userDb = userOptional.get();
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()){
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
