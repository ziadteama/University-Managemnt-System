package com.example.universitymanagementsystem;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/universitymanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

