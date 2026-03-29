package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.MovementTypeEnum;
import edu.school.hei.prog3__springingredient.entity.StockMovement;
import edu.school.hei.prog3__springingredient.entity.StockValue;
import edu.school.hei.prog3__springingredient.entity.UnitTypeEnum;
import edu.school.hei.prog3__springingredient.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementRepository repository;

    public StockMovementService(StockMovementRepository repository) {
        this.repository = repository;
    }

    public StockValue getStockValueOfIngredientAt(int id, Instant t, UnitTypeEnum unit) {
        List<StockMovement> stockMovementList = repository.findAllStockMovementsByIngredientId(id);
        List<StockValue> stockValues = stockMovementList.stream()
                .filter(stockMovement -> stockMovement.getCreationDatetime().isBefore(t))
                .peek(sm -> {
                    if (sm.getType() == MovementTypeEnum.OUT)
                        sm.getValue().setQuantity(sm.getValue().getQuantity() * -1);
                })
                .map(StockMovement::getValue)
                .toList();
        return (StockValue.builder()
                .quantity(stockValues.stream().mapToDouble(StockValue::getQuantity)
                        .sum())
                .unit(unit)
                .build());
    }
}

