package presentation;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.event.Event;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label loginMessageLabel;

    private Encrypt encrypt;
    private User user;
    private LoginService loginService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encrypt = new Encrypt();
        user=new User();
    }

    @FXML
    public void onExitClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onLoginClick(ActionEvent event) {
        loginChecker(event);
    }

    // Checks if the user is in the database (Correctly done in domain first) -LoginService
    private void loginChecker(Event event) {
        if (!usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            createLoginUser();
            boolean check = loginService.checkLoginforUser();
            if (check) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("start-view.fxml"));
                    ((Node) (event.getSource())).getScene().getWindow().setWidth(1200);
                    ((Node) (event.getSource())).getScene().getWindow().setHeight(800);
                    loginAnchorPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loginMessageLabel.setText("welcome");
            } else {

                loginMessageLabel.setText("It is not authenticated");
                loginMessageLabel.setTextFill(Color.RED);
            }
        } else {

            loginMessageLabel.setText("One of fields is empty");
            loginMessageLabel.setTextFill(Color.RED);
        }
    }
    private void createLoginUser() {
        loginService = new LoginService(usernameTextField.getText(), passwordTextField.getText());
    }
}
