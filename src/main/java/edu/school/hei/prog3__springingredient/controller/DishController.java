package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
