package models;

import java.sql.*;

public class DBConnect {
//    static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false&serverTimezone=UTC";
//    static final String USER = "fp510", PASS = "510";
//
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/init?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "123456";
    protected Connection connection;
    public Connection getConnection() {
        return connection;
    }
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    public DBConnect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
    }
}
