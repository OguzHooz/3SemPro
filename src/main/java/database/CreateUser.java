package database;

import domain.Encrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {

    private DatabaseConnection dbConnection;
    private Connection connection;
    private Encrypt encrypt;

    public CreateUser() {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        encrypt = new Encrypt();
    }

    public void createUserAccount(String username, String password, String email, String role) {
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO user_info "
                    + "(username, password, email, role) "
                    + "VALUES ('" + username + "','" + encrypt.encrypt(password) + "','"
                    + email + "','" + role + "')";
            st.executeQuery(sql);
            st.close();
        } catch (SQLException ex) {
            System.out.println("database.CreateUser: " + ex.getMessage());
        }
    }

}
