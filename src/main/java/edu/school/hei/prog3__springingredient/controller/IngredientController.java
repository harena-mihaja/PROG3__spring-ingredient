package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientController {
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping(value = {"/ingredients", "/ingredients/"})
    public ResponseEntity<?> getAllIngredients() {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllIngredients()));
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getIngredientById(id)));
    }

}
