package com.example.sempro;

import database.DatabaseConnection;
import domain.Encrypt;
import domain.BatchController;
import domain.CommandController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    Encrypt encrypt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encrypt = new Encrypt();
    }

    @FXML
    public void onExitClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onLoginClick(ActionEvent event) {

        DatabaseConnection connect = new DatabaseConnection();
        Connection connectdatabase = connect.getConnection();

        try {

            String checklogin = "SELECT FROM user_info WHERE username = '" + usernameTextField.getText() + "' AND password = '" + encrypt.encrypt(passwordTextField.getText()) + "'";

                Statement st = connectdatabase.createStatement();
                ResultSet queryresult = st.executeQuery(checklogin);


                    if(queryresult.next()){

                        AnchorPane pane = FXMLLoader.load(getClass().getResource("start-view.fxml"));
                        loginAnchorPane.getChildren().setAll(pane);

                    } else {
                        System.out.println("Wrong login");

                    }
                } catch (SQLException throwables) {
            throwables.printStackTrace();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    // Checks if the user is in the database (Correctly done in domain first) -LoginService
    private void loginChecker() {


    }
}
