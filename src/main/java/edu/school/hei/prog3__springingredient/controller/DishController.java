package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import edu.school.hei.prog3__springingredient.entity.Ingredient;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DishController {
    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping("/dishes")
    public ResponseEntity<?> getAllDishes() {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllDishes()));
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable int id,
            @RequestBody(required = false) List<Ingredient> ingredients) {
        if (ingredients == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request body is mandatory");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateDishIngredients(id, ingredients));
    }
}
