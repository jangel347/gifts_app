package com.jestean.springproject.msvc.gifts.controllers;

import com.jestean.springproject.msvc.gifts.models.entities.Gift;
import com.jestean.springproject.msvc.gifts.services.GiftService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gifts")
public class GiftController {
    @Autowired
    private GiftService service;

    @GetMapping("/listAll")
    public List<Gift> listAll(){
        return service.listAll();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Gift> giftOptional = service.getById(id);
        if (giftOptional.isPresent()) return ResponseEntity.ok(giftOptional.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Gift gift, BindingResult result){
        if (result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(gift));
    }

    @PostMapping("/createL")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createList(@Valid @RequestBody List<Gift> gifts, BindingResult result){
        if (result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(gifts));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Gift gift, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validate(result);
        }
        Optional<Gift> giftOptional = service.getById(id);
        if (giftOptional.isPresent()){
            Gift giftDb = giftOptional.get();
            giftDb.setName(gift.getName());
            giftDb.setDescription(gift.getDescription());
            giftDb.setLinks(gift.getLinks());
            giftDb.setPriority(gift.getPriority());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(giftDb));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Gift> giftOptional = service.getById(id);
        if (giftOptional.isPresent()){
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
