package edu.school.hei.prog3__springingredient.repository;

import edu.school.hei.prog3__springingredient.datasource.DataSource;
import edu.school.hei.prog3__springingredient.entity.CategoryEnum;
import edu.school.hei.prog3__springingredient.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class IngredientRepository {
    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAllIngredients() {
        String sql = "SELECT id, name, price, category FROM ingredient";
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Ingredient ingredient = Ingredient.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .price(rs.getDouble("price"))
                                .category(CategoryEnum.valueOf(rs.getString("category")))
                                .build();
                        ingredients.add(ingredient);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (ingredients);
    }

    public Ingredient findIngredientById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";
        Ingredient ingredient = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ingredient = Ingredient.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .price(rs.getDouble("price"))
                                .category(CategoryEnum.valueOf(rs.getString("category")))
                                .build();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (ingredient);
    }
}
