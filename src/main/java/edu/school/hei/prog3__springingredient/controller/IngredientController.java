package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.entity.Ingredient;
import edu.school.hei.prog3__springingredient.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<?> getAllIngredients()
    {
        try {
            return (ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getAllIngredients()));
        } catch (RuntimeException e) {
            return (ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage()));
        }
    }
}
