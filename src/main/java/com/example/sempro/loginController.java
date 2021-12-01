package com.example.sempro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {
    @FXML
    private TextField userNameLabel;

    @FXML
    private TextField passWordLabel;

    @FXML
    private Button loginButton;

    public void Login(ActionEvent actionEvent) {
        /** !passWordLabel.getText().isEmpty() && !userNameLabel.getText().isEmpty()*/
        try {

            HelloApplication.setRoot("start-view");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
