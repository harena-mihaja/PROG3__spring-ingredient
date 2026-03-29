package edu.school.hei.prog3__springingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish {
    private int id;
    private String name;
    private DishTypeEnum dishType;
    private Double price;
}
