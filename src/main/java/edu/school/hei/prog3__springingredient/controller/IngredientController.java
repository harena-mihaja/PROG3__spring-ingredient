package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.entity.UnitTypeEnum;
import edu.school.hei.prog3__springingredient.service.IngredientService;
import edu.school.hei.prog3__springingredient.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    private final StockMovementService stockMovementService;

    public IngredientController(IngredientService ingredientService, StockMovementService stockMovementService) {
        this.ingredientService = ingredientService;
        this.stockMovementService = stockMovementService;
    }

    @GetMapping(value = {"/ingredients", "/ingredients/"})
    public ResponseEntity<?> getAllIngredients() {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.getAllIngredients()));
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.getIngredientById(id)));
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStockValueOfIngredientAt(@PathVariable Integer id, @RequestParam("at") Instant temporal, @RequestParam("unit") UnitTypeEnum unit) {
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(stockMovementService.getStockValueOfIngredientAt(id, temporal, unit)));
    }
}
