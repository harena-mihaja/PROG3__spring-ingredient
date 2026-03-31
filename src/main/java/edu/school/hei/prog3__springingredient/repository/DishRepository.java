package edu.school.hei.prog3__springingredient.repository;

import edu.school.hei.prog3__springingredient.datasource.DataSource;
import edu.school.hei.prog3__springingredient.entity.Dish;
import edu.school.hei.prog3__springingredient.entity.DishTypeEnum;
import edu.school.hei.prog3__springingredient.entity.Ingredient;
import edu.school.hei.prog3__springingredient.entity.CategoryEnum;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {
    private final DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> findAllDishes() {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish";
        List<Dish> dishes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Dish dish = Dish.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .dishType(DishTypeEnum.valueOf(rs.getString("dish_type")))
                                .price(rs.getObject("selling_price") == null ? null : rs.getDouble("selling_price"))
                                .build();
                        dishes.add(dish);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (dishes);
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM dish WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> findIngredientsByDishId(int dishId) {
        String sql = "SELECT i.id, i.name, i.price, i.category FROM ingredient i INNER JOIN dishingredient di ON i.id = di.id_ingredient WHERE di.id_dish = ?";
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredients.add(Ingredient.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .price(rs.getDouble("price"))
                            .category(CategoryEnum.valueOf(rs.getString("category")))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public void associateIngredients(int dishId, List<Integer> ingredientIdsToInsert) {
        if (ingredientIdsToInsert.isEmpty()) return;
        String sql = "INSERT INTO dishingredient (id_dish, id_ingredient) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer id : ingredientIdsToInsert) {
                ps.setInt(1, dishId);
                ps.setInt(2, id);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dissociateIngredients(int dishId, List<Integer> ingredientIdsToDelete) {
        if (ingredientIdsToDelete.isEmpty()) return;
        String sql = "DELETE FROM dishingredient WHERE id_dish = ? AND id_ingredient = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer id : ingredientIdsToDelete) {
                ps.setInt(1, dishId);
                ps.setInt(2, id);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
