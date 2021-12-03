package domain;

public class User {

    private int userID;
    private String role;
    private String username;
    private String password;
    private String email;

    public User(int userID,
                String role,
                String username,
                String password,
                String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
