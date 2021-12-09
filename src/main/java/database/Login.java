package database;

import domain.Encrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class Login {
     Encrypt encrypt;
    DatabaseConnection connect;
    // DatabaseConnection connect = new DatabaseConnection();
   // Connection connectdatabase = connect.getConnection();

    public  Login(){
        encrypt=new Encrypt();
        this.connect=new DatabaseConnection();

    }

    public boolean checkUser(String username, String password) {

        boolean check = false;

        try {
            String sql = "SELECT username,password FROM user_info WHERE username = '" + username + "' AND password = '" + encrypt.encrypt(password) + "'";

            Statement st = connect.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                check = true;
                System.out.println("RS.next(): " + check);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Login: " + check);
        return check;
    }

    public boolean checkWorkerorGuest(String role){

        boolean check = false;

        try{
            String sql = "SELECT role FROM user_info WHERE role = '" + role +"'";
            Statement st = connect.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                check = true;
                System.out.println("Role is check in DB"+check);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Role is not  check in DB"+check);
        return check;
    }


}




