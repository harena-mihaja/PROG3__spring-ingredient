package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.Dish;
import edu.school.hei.prog3__springingredient.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAllDishes() {
        return (repository.findAllDishes());
    }
}
