package database;

import domain.Encrypt;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private DatabaseConnection databaseConnection;
    private Encrypt encrypt;

    public Login() {
        this.databaseConnection=new DatabaseConnection();
        this.encrypt=new Encrypt();
    }
    public boolean checkUserinDB(String loginUsername, String loginPassword){

        boolean checked = false;
        String sql = "SELECT username,password FROM user_info WHERE username='" + loginUsername + "' AND password='" +encrypt.encrypt(loginPassword) + "'";
        try {
            ResultSet rs = databaseConnection.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                checked = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return checked;
    }

}
