package edu.school.hei.prog3__springingredient.controller;

import edu.school.hei.prog3__springingredient.entity.UnitTypeEnum;
import edu.school.hei.prog3__springingredient.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class StockMovementController {
   private final StockMovementService service;

    public StockMovementController(StockMovementService service) {
        this.service = service;
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStockValueOfIngredientAt(@PathVariable Integer id, @RequestParam(value = "at", required = false) Instant temporal, @RequestParam(value = "unit", required = false) UnitTypeEnum unit) {
        if (temporal == null || unit == null)
            return (ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Either mandatory query parameter `at` or `unit` is not provided"));
        return (ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getStockValueOfIngredientAt(id, temporal, unit)));
    }
}
