package edu.school.hei.prog3__springingredient.repository;

import edu.school.hei.prog3__springingredient.datasource.DataSource;
import edu.school.hei.prog3__springingredient.entity.Dish;
import edu.school.hei.prog3__springingredient.entity.DishTypeEnum;
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
}
