package com.example.sempro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        welcomeText.setTextFill(Color.RED);

    }
}