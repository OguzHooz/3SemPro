package com.example.sempro;

import database.DatabaseConnection;
import domain.Encrypt;
import domain.LoginService;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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
    @FXML
    private Label checkin;

     private Encrypt encrypt;
     LoginService loginService;
     private StartView startView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encrypt = new Encrypt();
        startView = new StartView();
    }

    @FXML
    public void onExitClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onLoginClick(ActionEvent event) {
        loginChecker();
    }

    // Checks if the user is in the database (Correctly done in domain first) -LoginService
    private void loginChecker() {

        if (!usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            //loginService = new LoginService(usernameTextField.getText(), encrypt.encrypt(passwordTextField.getText()));
            createcheck();

            boolean check = loginService.validateLogin();
            if (check) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("start-view.fxml"));
                    loginAnchorPane.getChildren().setAll(pane);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkin.setText("User can not come in the system");
            } else {
                checkin.setText("User is not authenticated");
          }
        }
        else{
            checkin.setText("one of fields is empty");
        }
    }
    public void createcheck(){
        loginService=new LoginService(usernameTextField.getText(),passwordTextField.getText());
    }


}

