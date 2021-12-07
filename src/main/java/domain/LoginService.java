package domain;

import database.CreateUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class LoginService {


    private String username;
    private String password;

  //  Encrypt encrypt = new Encrypt();

  //  String sql = "SELECT FROM user_info WHERE username = '" + username + "' AND password = '" + encrypt.encrypt(password) + "'";


   private CreateUser createUser=new CreateUser();

    public LoginService(String username,String password) {
        this.username = username;
        this.password = password;

    }


   // public boolean validateLogin(){
    //   return

   // }

    //Tjekker om brugeren er i databasen og se om username og password passer sammen.

}
