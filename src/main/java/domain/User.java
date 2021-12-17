package domain;

import database.CreateUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private int userID;
    private String role;
    private String username;
    private String password;
    private String email;

    public User() {

    }

    public User(int userID,
                String username,
                String password,
                String email,
                String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }

//    public User() {
//
//    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
