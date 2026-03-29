package edu.school.hei.prog3__springingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockValue {
    private double quantity;
    private UnitTypeEnum unit;
}
