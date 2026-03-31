package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.Dish;
import edu.school.hei.prog3__springingredient.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import edu.school.hei.prog3__springingredient.repository.IngredientRepository;
import edu.school.hei.prog3__springingredient.entity.Ingredient;
import edu.school.hei.prog3__springingredient.exception.NotFoundException;

@Service
public class DishService {
    private final DishRepository repository;
    private final IngredientRepository ingredientRepository;

    public DishService(DishRepository repository, IngredientRepository ingredientRepository) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> getAllDishes() {
        return (repository.findAllDishes());
    }

    public List<Ingredient> updateDishIngredients(int dishId, List<Ingredient> requestedIngredients) {
        if (!repository.existsById(dishId)) {
            throw new NotFoundException("Dish.id=" + dishId + " is not found");
        }

        List<Integer> requestedIds = requestedIngredients.stream()
                .map(Ingredient::getId)
                .distinct()
                .toList();

        List<Integer> validRequestedIds = new ArrayList<>();
        for (Integer id : requestedIds) {
            if (ingredientRepository.findIngredientById(id) != null) {
                validRequestedIds.add(id);
            }
        }

        List<Integer> currentKeys = repository.findIngredientsByDishId(dishId).stream()
                .map(Ingredient::getId)
                .toList();

        List<Integer> toDelete = new ArrayList<>(currentKeys);
        toDelete.removeAll(validRequestedIds);

        List<Integer> toInsert = new ArrayList<>(validRequestedIds);
        toInsert.removeAll(currentKeys);

        repository.dissociateIngredients(dishId, toDelete);
        repository.associateIngredients(dishId, toInsert);

        return repository.findIngredientsByDishId(dishId);
    }
}
