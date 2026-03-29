package edu.school.hei.prog3__springingredient.datasource;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public class DataSource {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public Connection getConnection()
    {
        try{
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
