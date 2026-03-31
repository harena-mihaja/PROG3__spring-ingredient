package edu.school.hei.prog3__springingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovement {
    private int id;
    private Double quantity;
    private UnitTypeEnum unit;
    private MovementTypeEnum type;
    private Instant creationDatetime;
}
