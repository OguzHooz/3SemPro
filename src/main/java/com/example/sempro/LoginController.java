package com.example.sempro;

import domain.BatchController;
import domain.CommandController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private RadioButton simRB;

    @FXML
    private RadioButton macRB;

    private StartView startView;

    private static LoginController instance = new LoginController();
    public static LoginController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startView = StartView.getInstance();
    }

    @FXML
    public void onExitClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onLoginClick(ActionEvent event) {
        try {
            selectServer();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("start-view.fxml"));
            loginAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RadioButton getSimRB() {
        return simRB;
    }

    public RadioButton getMacRB() {
        return macRB;
    }

    // Checks if the user is in the database (Correctly done in domain first) -LoginService
    private void loginChecker() {
    }

    private void selectServer() {
        if (simRB.isSelected()) {
            startView.setHost("127.0.0.1");
            startView.setPort(4840);
        } else if (macRB.isSelected()) {
            startView.setHost("192.168.0.122");
            startView.setPort(4840);
        }
    }

}
