package edu.school.hei.prog3__springingredient.service;

import edu.school.hei.prog3__springingredient.entity.Ingredient;
import edu.school.hei.prog3__springingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAllIngredients()
    {
        return (repository.findAllIngredients());
    }
}
