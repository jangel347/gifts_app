package com.jestean.springproject.msvc.usuarios.controllers;

import com.jestean.springproject.msvc.usuarios.models.entities.User;
import com.jestean.springproject.msvc.usuarios.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return validate(result);
        }
        if (!user.getEmail().isEmpty() && service.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error","The email is already in use"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }   

    @PostMapping("/createL")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createList(@Valid @RequestBody List<User> users, BindingResult result){
        if (result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(users));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validate(result);
        }
        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()){
            User userDb = userOptional.get();
            if (!user.getEmail().isEmpty() && service.findByEmail(user.getEmail()).isPresent() && !user.getEmail().equalsIgnoreCase(userDb.getEmail())){
                return ResponseEntity.badRequest().body(Collections.singletonMap("error","The email is already in use"));
            }
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            userDb.setBirth_date(user.getBirth_date());
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

    private ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(
                err.getField(), 
                "Field: " + err.getField() + " " + err.getDefaultMessage())
            );
        return ResponseEntity.badRequest().body(errors);
    }
}
