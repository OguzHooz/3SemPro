package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Use this if on simulation
        //FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-view.fxml"));

        // Use this if on machine
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("start-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("B&R");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}