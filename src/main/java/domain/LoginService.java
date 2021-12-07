package domain;

import database.CreateUser;
import database.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class LoginService {


    private String username;
    private String password;
    private User user;
    private Login login;


    public LoginService(String username, String password) {
        this.username = username;
        this.password = password;
        login = new Login();
        user = new User();
    }


    public boolean validateLogin() {
        boolean check = false;
            if (login.checkUser(username,password)) {
                check = true;
                user = new User(username, password);
                System.out.println("The User is in DB");
            }
            else {

            check = false;

           System.out.println("The User is not in DB");
        }
        return check;
        }


        //Tjekker om brugeren er i databasen og se om username og password passer sammen.


}
