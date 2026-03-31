package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.*;
import edu.school.hei.prog3__springingredient.exception.NotFoundException;
import edu.school.hei.prog3__springingredient.repository.IngredientRepository;
import edu.school.hei.prog3__springingredient.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementRepository repository;
    private final IngredientRepository ingredientRepository;

    public StockMovementService(StockMovementRepository repository, IngredientRepository ingredientRepository) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
    }

    public StockValue getStockValueOfIngredientAt(int id, Instant t, UnitTypeEnum unit) {
        if (ingredientRepository.findIngredientById(id) == null)
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        List<StockMovement> stockMovementList = repository.findAllStockMovementsByIngredientId(id);
        return (StockValue.builder()
                .quantity(stockMovementList.stream()
                    .filter(stockMovement -> stockMovement.getCreationDatetime().isBefore(t))
                    .peek(sm -> {
                        if (sm.getType() == MovementTypeEnum.OUT) sm.setQuantity(sm.getQuantity() * -1);
        })
                   .mapToDouble(StockMovement::getQuantity)
                        .sum())
                .unit(unit)
                .build());
    }

    public List<StockMovement> getStockMovementsFromTo(int id, Instant from, Instant to) {
        if (ingredientRepository.findIngredientById(id) == null)
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        return (repository.findAllStockMovementsByIngredientId(id).stream()
                .filter(sm -> sm.getCreationDatetime().isAfter(from) && sm.getCreationDatetime().isBefore(to)))
                .toList();
    }

    public List<StockMovement> createStockMovements(int id, List<StockMovementInput> newStockMovementList)
    {
        if (ingredientRepository.findIngredientById(id) == null)
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        return (repository.saveNewStockMovements(id, newStockMovementList));
    }
}

