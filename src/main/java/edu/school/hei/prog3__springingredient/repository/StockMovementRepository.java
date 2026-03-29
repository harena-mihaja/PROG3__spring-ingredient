package edu.school.hei.prog3__springingredient.repository;

import edu.school.hei.prog3__springingredient.datasource.DataSource;
import edu.school.hei.prog3__springingredient.entity.MovementTypeEnum;
import edu.school.hei.prog3__springingredient.entity.StockMovement;
import edu.school.hei.prog3__springingredient.entity.StockValue;
import edu.school.hei.prog3__springingredient.entity.UnitTypeEnum;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementRepository {
    private final DataSource dataSource;

    public StockMovementRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<StockMovement> findAllStockMovementsByIngredientId(int id) {
        String sql =
                """
                        SELECT id, id_ingredient, quantity, type, unit, creation_datetime FROM stockmovement
                        WHERE id_ingredient = ?
                        """;
        List<StockMovement> stockMovementList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        StockValue stockValue = StockValue.builder()
                                .unit(UnitTypeEnum.valueOf(rs.getString("unit")))
                                .quantity(rs.getDouble("quantity"))
                                .build();
                        StockMovement stockMovement = StockMovement.builder()
                                .id(rs.getInt("id"))
                                .type(MovementTypeEnum.valueOf(rs.getString("type")))
                                .creationDatetime(rs.getTimestamp("creation_datetime").toInstant())
                                .value(stockValue)
                                .build();
                        stockMovementList.add(stockMovement);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (stockMovementList);
    }
}
