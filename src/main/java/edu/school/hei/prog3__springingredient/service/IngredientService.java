package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.Ingredient;
import edu.school.hei.prog3__springingredient.exception.NotFoundException;
import edu.school.hei.prog3__springingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAllIngredients() {
        return (repository.findAllIngredients());
    }

    public Ingredient getIngredientById(Integer id) {
        Ingredient ingredient = repository.findIngredientById(id);
        if (ingredient == null)
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        return (ingredient);
    }
}
