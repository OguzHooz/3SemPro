package com.example.sempro;

import domain.BatchController;
import domain.CommandController;
import domain.Subscription;
import database.DatabaseConnection;
import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    public ToggleGroup simORmac;
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
    private Label loginMessageLabel;

    private Encrypt encrypt;
    private User user;
    private LoginService loginService;

    private StartView startView;

    private static LoginController instance = new LoginController();
    public static LoginController getInstance() {
        return instance;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encrypt = new Encrypt();
        user=new User();

        startView = StartView.getInstance();
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
            createLoginUser();
            boolean check = loginService.checkLoginforUser();
            if (check) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("start-view.fxml"));
                    loginAnchorPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loginMessageLabel.setText("welcome");
            } else {

                loginMessageLabel.setText("It is not authenticated");
                loginMessageLabel.setTextFill(Color.RED);
            }
        }else {

            loginMessageLabel.setText("One of fields is empty");
            loginMessageLabel.setTextFill(Color.RED);
        }
    }
    private void createLoginUser() {
        loginService = new LoginService(usernameTextField.getText(), passwordTextField.getText());
    }
}
