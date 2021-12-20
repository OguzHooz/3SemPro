package database;

import domain.Encrypt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.User;

public class CreateUser {

    private DatabaseConnection dbConnection;
    private Connection connection;
    private Encrypt encrypt;
    private ResultSet rs;
    private User user;

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

    public List getUserInfo() {
        List<User> userList = new ArrayList<>();
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                rs = st.executeQuery("SELECT * FROM user_info");
                while (rs.next()) {
                    userList.add(new User(rs.getInt("userid"), rs.getString("username"),
                            rs.getString("password"), rs.getString("email"),
                            rs.getString("role")));
                }
                st.close();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("No connection to database");
        }

        return userList;

    }

    public int getUserID(int userID) {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT speed FROM batchreport WHERE batchid = " + userID;
            rs = st.executeQuery(sql);
            int userinfoid = 0;
            while (rs.next()) {
                userinfoid = rs.getInt("userid");
            }
            st.close();
            return userinfoid;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public String getUsername(int userID) {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT username FROM user_info WHERE userid = " + userID;
            rs = st.executeQuery(sql);
            String username = "";
            while (rs.next()) {
                username = rs.getString("username");
            }
            st.close();
            return username;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public String getPassword(int userID) {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT password FROM user_info WHERE userid = " + userID;
            rs = st.executeQuery(sql);
            String password = "";
            while (rs.next()) {
                password = rs.getString("password");
            }
            st.close();
            return password;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public String getEmail(int userID) {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT email FROM user_info WHERE userid = " + userID;
            rs = st.executeQuery(sql);
            String email = "";
            while (rs.next()) {
                email = rs.getString("email");
            }
            st.close();
            return email;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public String getRole(int userID) {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT role FROM user_info WHERE userid = " + userID;
            rs = st.executeQuery(sql);
            String role = "";
            while (rs.next()) {
                role = rs.getString("role");
            }
            st.close();
            return role;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public void updateUser(int userID, String username, String password, String email, String role) {
        try {
            Statement st = connection.createStatement();
            String sql = "UPDATE user_info SET username = '" +
                    username + "', password = '" + password +
                    "', email = '" + email + "', role = '" + role + "' WHERE userid = " + userID;
            st.executeUpdate(sql);
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteUserinDB(int userID) {

        try {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM user_info WHERE userid= '" + userID + "'";
            st.executeUpdate(sql);
            st.close();
            System.out.println("Batch ID: " + userID + "deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
