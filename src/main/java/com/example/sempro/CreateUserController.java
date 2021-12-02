package com.example.sempro;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CreateUserController {

    @FXML
    private TextField usernameTxtField;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private PasswordField confirmPasswordTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private RadioButton managerRadioBtn;

    @FXML
    private RadioButton workerRadioBtn;

    @FXML
    private RadioButton guestRadioBtn;

    @FXML
    private Label alertLbl;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelOnClick(ActionEvent event) {

    }

    @FXML
    void confirmOnClick(ActionEvent event) {

    }

    @FXML
    void guestRB(ActionEvent event) {

    }

    @FXML
    void managerRB(ActionEvent event) {

    }

    @FXML
    void workerRB(ActionEvent event) {

    }

}
