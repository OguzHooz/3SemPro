package domain;

import database.Login;

public class LoginService {
    private String username;
    private String password;
    private User user;
    private Login login;
    public LoginService(String username, String password) {
        user=new User();
        login=new Login();
        this.username=username;
        this.password=password;

    }

    public boolean checkLoginforUser(){
       boolean checkLogin ;
       login=new Login();
       if (login.checkUserinDB(username, password)){
           checkLogin=true;
           user = new User(username,password);
           System.out.println("The user name is: "+user.getUsername() +"\n" +"The password of user name is: "+user.getPassword());
           System.out.println("User is checked in DB!");
       }
       else{
            checkLogin = false;
           System.out.println(" User does not find in systemt");
        }
       return checkLogin;
       }

   // }

    //Tjekker om brugeren er i databasen og se om username og password passer sammen.

}
