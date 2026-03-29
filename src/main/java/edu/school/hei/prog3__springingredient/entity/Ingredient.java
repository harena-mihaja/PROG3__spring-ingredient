package edu.school.hei.prog3__springingredient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient {
    private int id;
    private String name;
    private Double price;
    private CategoryEnum category;
    @JsonIgnore
    private List<StockMovement> stockMovementList;
}
