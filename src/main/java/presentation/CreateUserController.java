package presentation;

import domain.CreateUserService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    private StartView startView;

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
    private Button cancelBtn;

    private CreateUserService create;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create = new CreateUserService();
        alertLbl.setVisible(false);
        startView=new StartView();
    }

    @FXML
    public void cancelOnClick(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmOnClick(ActionEvent event) {

        TextField[] textFields = {usernameTxtField, passwordTxtField, confirmPasswordTxtField, emailTxtField};
        for (TextField text : textFields) {
            if (text.getText().isEmpty()) {
                alertLbl.setText("Please fill out everything");
                alertLbl.setDisable(false);
                alertLbl.setVisible(true);
                return;
            }
        }

        if (!emailValidation()) {
            alertLbl.setText("Invalid e-mail");
            alertLbl.setDisable(false);
            alertLbl.setVisible(true);
            return;
        }

        if (!passwordTxtField.getText().equals(confirmPasswordTxtField.getText())) {
            alertLbl.setText("Password does not match");
            alertLbl.setDisable(false);
            alertLbl.setVisible(true);
            return;
        }

        if (managerRadioBtn.isSelected()) {
            create.createUser(usernameTxtField.getText(), passwordTxtField.getText(), emailTxtField.getText(), managerRadioBtn.getText());
        } else if (workerRadioBtn.isSelected()) {
            create.createUser(usernameTxtField.getText(), passwordTxtField.getText(), emailTxtField.getText(), workerRadioBtn.getText());
        } else if (guestRadioBtn.isSelected()) {
            create.createUser(usernameTxtField.getText(), passwordTxtField.getText(), emailTxtField.getText(), guestRadioBtn.getText());
        }
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();


    }

    private boolean emailValidation() {
        if (emailTxtField.getText().contains("@") == false || emailTxtField.getText().contains(".") == false) {
            return false;
        }
        return true;
    }

}
