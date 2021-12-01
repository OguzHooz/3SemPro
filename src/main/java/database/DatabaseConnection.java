package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;
    private final String url = "jdbc:postgresql://ella.db.elephantsql.com:5432/fyhpiwab";
    private final String user = "fyhpiwab";
    private final String pass = "N2GjdahOuVFDnz5-lFSPmdm90AGGQ3uA";

    public DatabaseConnection() {
        connect();
    }

    private Connection connect() {
        if(connection != null){
            return connection;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

}
