package edu.school.hei.prog3__springingredient.repository;

import edu.school.hei.prog3__springingredient.datasource.DataSource;
import edu.school.hei.prog3__springingredient.entity.MovementTypeEnum;
import edu.school.hei.prog3__springingredient.entity.StockMovement;
import edu.school.hei.prog3__springingredient.entity.StockMovementInput;
import edu.school.hei.prog3__springingredient.entity.UnitTypeEnum;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
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
                        StockMovement stockMovement = StockMovement.builder()
                                .id(rs.getInt("id"))
                                .type(MovementTypeEnum.valueOf(rs.getString("type")))
                                .creationDatetime(rs.getTimestamp("creation_datetime").toInstant())
                                .quantity(rs.getDouble("quantity"))
                                .unit(UnitTypeEnum.valueOf(rs.getString("unit")))
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

    public List<StockMovement> saveNewStockMovements(int id, List<StockMovementInput> newStockMovementList) {
       String sql = "INSERT INTO stockmovement (id_ingredient, quantity, type, unit, creation_datetime) VALUES (?, ?, ?::movement_type, ?::unit_type, ?)";
       List<StockMovement> createdMovements = new ArrayList<>();
       try (Connection connection = dataSource.getConnection()) {
           try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
               Instant now = Instant.now();
               for (StockMovementInput st : newStockMovementList) {
                    ps.setInt(1, id);
                    ps.setDouble(2, st.getQuantity());
                    ps.setString(3, st.getType().name());
                    ps.setString(4, st.getUnit().name());
                    ps.setTimestamp(5, Timestamp.from(now));
                    ps.addBatch();
               }
               ps.executeBatch();
               try (ResultSet rs = ps.getGeneratedKeys()) {
                   int index = 0;
                   while (rs.next()) {
                       StockMovementInput input = newStockMovementList.get(index++);
                       StockMovement movement = StockMovement.builder()
                               .id(rs.getInt(1))
                               .quantity(input.getQuantity())
                               .unit(input.getUnit())
                               .type(input.getType())
                               .creationDatetime(now)
                               .build();
                       createdMovements.add(movement);
                   }
               }
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return createdMovements;
    }
}
