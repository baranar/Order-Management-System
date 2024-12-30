package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    //Singleton Design Pattern
    public static DbHelper instance = null;
    private Connection connection = null;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;

    //constructor
    public DbHelper() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //getter method
    private Connection getConnection(){
        return connection;
    }

    public static Connection getInstance(){
        try {
            if (instance ==null || instance.getConnection().isClosed()) {   // I want to create the Database Connection just once
                instance = new DbHelper();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }
}