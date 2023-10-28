package com.fb.facebook.connection;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

@Getter
public class DBConnection {
    private Connection connection;
    protected String user = "root";
    protected String password = "Evastorm1";
    protected String dbClass = "com.mysql.cj.jdbc.Driver";
    protected String dbUrl = "jdbc:mysql://localhost:3306/facebookdb";

    public void initializeConnection() {
        try {
            Class.forName(dbClass);
            connection = DriverManager.getConnection(dbUrl, user, password);
            if(connection != null) {
                System.out.println("Connected to DB!");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
