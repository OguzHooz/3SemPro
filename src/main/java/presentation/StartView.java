package presentation;

import domain.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;

import domain.BatchController;
import domain.BatchReport;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    private ISubscription subscribe;
    private BatchReport batchReport;
    private OEE oee;
    private User user;
    private CreateUserService createUserService;
    private Textfile textfile;
    private DateTimeFormatter dtf;
    private Timeline timeLine;
    private LocalTime localTime;


    //FXML
    @FXML
    private AnchorPane startViewAnchorPane;

    @FXML
    private TableView<User> userManagementTable;

    @FXML
    private Label acceptedLabel;

    @FXML
    private Label amountCurrentBatchLabel;

    @FXML
    private TextField amountToProduceTextField;

    @FXML
    private Label batchLabel;

    @FXML
    private Label defectiveLabel;

    @FXML
    private Button exitBtn;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label idleTimeLabel;

    @FXML
    private Label producedLabel;

    @FXML
    private ChoiceBox productIDChoiceBox;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField speedTextField;

    @FXML
    private Button startBtn;

    @FXML
    private Label tempLabel;

    @FXML
    private Label vibrationLabel;

    @FXML
    private Label productTypeLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label timeOnLabel;

    @FXML
    private Label invalidInputLabel;

    @FXML
    private Label companyBRLabel;
    @FXML
    private Label batchIDBRlabel;

    @FXML
    private Label amountProducedBRLabel;

    @FXML
    private Label amountToProduceBRLabel;

    @FXML
    private Label productTypeBRLabel;

    @FXML
    private Label speedBRLabel;

    @FXML
    private Label acceptedBRLabel;

    @FXML
    private Label defectedBRLabel;

    @FXML
    private Label idleTimeBRLabel;

    @FXML
    private Label timeOnBRLabel;

    @FXML
    private Label startTimeBRLabel;

    @FXML
    private CheckBox optimalSpeedBox;

    @FXML
    private Label setCompanyLabel;

    @FXML
    private TextField companyTextField;
    
    @FXML
    private Label setoeeLabel;

    @FXML
    private Label batchReportInvalid;
    
    @FXML
    private Label updateUserIDLabel;

    @FXML
    private TextField updateUsernameTextField;

    @FXML
    private PasswordField updatePasswordTextField;

    @FXML
    private TextField updateEmailTextField;

    @FXML
    private RadioButton updateRManagerRB;

    @FXML
    private RadioButton updateRWorkerRB;

    @FXML
    private RadioButton updateRGuestRB;

    @FXML
    private TableColumn<User, Integer> userIDColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableView<BatchReport> tabelViewBR;
    @FXML
    private TableColumn<BatchReport, String> companyColumn;

    @FXML
    private TableColumn<BatchReport, Integer> batchidColumn;

    @FXML
    private TableColumn<BatchReport, Integer> amountproducedColumn;

    @FXML
    private TableColumn<BatchReport, String> amounttoproduceColumn;

    @FXML
    private TableColumn<BatchReport, String> productTypeColumn;

    @FXML
    private TableColumn<BatchReport, Integer> speedColumn;

    @FXML
    private TableColumn<BatchReport, Integer> acceptedColumn;

    @FXML
    private TableColumn<BatchReport, Integer> defectedColumn;

    @FXML
    private TableColumn<BatchReport, String> IdletimeColumn;

    @FXML
    private TableColumn<BatchReport, String> timeonColumn;

    @FXML
    private TableColumn<BatchReport, String> starttimeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cmdCtrl = new CommandController();
        this.batchCtrl = new BatchController();
        this.subscribe = new Subscription();
        this.batchReport = new BatchReport();
        this.oee = new OEE();
        this.user = new User();
        this.createUserService = new CreateUserService();
        textfile = new Textfile();
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse("00:00:00");
        timeLine = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeLine.setCycleCount(Animation.INDEFINITE);
        consumerGUI();
        fillComboBox();
        tableViewBR();
        getInforfraControl();
        tableViewUser();
    }
    public void tableViewBR(){
        columnsBatchReport();
        if (tabelViewBR.getSelectionModel().getSelectedItems() != null) {
            tabelViewBR.setItems(batchReport.getInformationBR());
        } else {
            System.out.println("Choose a column");
        }
    }
    public void tableViewUser(){
        columnsUserManagement();
        userManagementTable.setItems(createUserService.getInfoUser());
    }

    public void columnsBatchReport() {
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        batchidColumn.setCellValueFactory(new PropertyValueFactory<>("batchid"));
        amountproducedColumn.setCellValueFactory(new PropertyValueFactory<>("amountProduced"));
        amounttoproduceColumn.setCellValueFactory(new PropertyValueFactory<>("amountToProduce"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
        defectedColumn.setCellValueFactory(new PropertyValueFactory<>("defected"));
        IdletimeColumn.setCellValueFactory(new PropertyValueFactory<>("idleTime"));
        timeonColumn.setCellValueFactory(new PropertyValueFactory<>("timeOn"));
        starttimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    }

    public void columnsUserManagement() {
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }


    public void getInforfraControl(){
        companyBRLabel.setText(companyTextField.getText());
        amountProducedBRLabel.setText(producedLabel.getText());
        batchIDBRlabel.setText(batchLabel.getText());
        amountToProduceBRLabel.setText(amountCurrentBatchLabel.getText());
        productTypeBRLabel.setText(productTypeLabel.getText());
        speedBRLabel.setText(speedLabel.getText());
        acceptedBRLabel.setText(acceptedLabel.getText());
        defectedBRLabel.setText(defectiveLabel.getText());
        idleTimeBRLabel.setText(idleTimeLabel.getText());
        timeOnBRLabel.setText(timeOnLabel.getText());
        startTimeBRLabel.setText(timeOnLabel.getText());
        int acceptedCount = batchCtrl.getProducedCount() - batchCtrl.getDefectiveCount();
        acceptedLabel.setText(Float.toString(acceptedCount));
    }

    @FXML
    public void updateInfo(){
        getInforfraControl();
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        batchReport.BatchReportDM(companyBRLabel.getText(), Float.parseFloat(amountProducedBRLabel.getText()), Float.parseFloat(amountToProduceBRLabel.getText()),
        productTypeBRLabel.getText(), Float.parseFloat(speedBRLabel.getText()), Float.parseFloat(acceptedBRLabel.getText()), Float.parseFloat(defectedBRLabel.getText()), idleTimeBRLabel.getText(),
        timeOnBRLabel.getText(), startTimeBRLabel.getText());

        textfile.createTextfile(companyBRLabel.getText(), 12,Float.parseFloat(amountProducedBRLabel.getText()), Float.parseFloat(amountToProduceBRLabel.getText()),
                productTypeBRLabel.getText(), Float.parseFloat(speedBRLabel.getText()), Float.parseFloat(acceptedBRLabel.getText()), Float.parseFloat(defectedBRLabel.getText()), idleTimeBRLabel.getText(),
                timeOnBRLabel.getText(), startTimeBRLabel.getText());
        tableViewBR();
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        tableViewBR();
    }

  @FXML
  void deletecolumnOnAction(ActionEvent event) {;

      if (tabelViewBR.getSelectionModel().getSelectedItem() != null) {
          batchReport = (BatchReport) tabelViewBR.getSelectionModel().getSelectedItem();
          batchReport.deleteeReportinDM(batchReport.getBatchid());
          tableViewBR();
      } else {
          System.out.println("GUI: TabelView NULL");
      }

  }

    @FXML
    public void onStartClick() {

        try {
            speedLabel.setText(cmdCtrl.getSpeed().toString());
            batchLabel.setText(batchCtrl.getBatchId().toString());
            amountCurrentBatchLabel.setText(batchCtrl.getAmountToProduce().toString());
            setProductTypeLabel();

            //Only works with simulation - can't connect to database while connected to machine.
            batchLabel.setText((batchReport.getBatchID() + 1) + "");

            cmdCtrl.start();
            startTimeLabel.setText(dtf.format(java.time.LocalTime.now()));


            localTime = LocalTime.parse("00:00:00");
            timeOnLabel.setText(localTime.format(dtf));
            timeLine.play();
            startBtn.setDisable(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onStopClick(ActionEvent event) {
       getInforfraControl();
        cmdCtrl.stop();
        int acceptedCount = batchCtrl.getProducedCount() - batchCtrl.getDefectiveCount();
        acceptedLabel.setText(Float.toString(acceptedCount));
        if (startBtn.isDisable()) {
            timeLine.stop();
            startBtn.setDisable(false);
        }
    }

    @FXML
    public void onClearClick(ActionEvent actionEvent) {
        cmdCtrl.clear();
    }

    @FXML
    public void onResetClick(ActionEvent actionEvent) {
        batchLabel.setText("0");
        producedLabel.setText("0");
        acceptedLabel.setText("0");
        amountCurrentBatchLabel.setText("0");
        defectiveLabel.setText("0");
        speedLabel.setText("0");
        productTypeLabel.setText("0");
        cmdCtrl.reset();
    }

    @FXML
    public void onAbortClick(ActionEvent actionEvent) {
        cmdCtrl.abort();
    }

    @FXML
    public void onExitClick(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            ((Node) (event.getSource())).getScene().getWindow().setWidth(600);
            ((Node) (event.getSource())).getScene().getWindow().setHeight(400);
            startViewAnchorPane.getChildren().setAll(pane);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void createUserOnAction(ActionEvent event) {
        Stage stage;
        Parent root;
        try {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("createUser-view.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void changeOnAction(ActionEvent actionEvent) {
        if (!amountToProduceTextField.getText().isEmpty() &&
                (!speedTextField.getText().isEmpty() || optimalSpeedBox.isSelected()) &&
                amountToProduceTextField.getText().matches("[0-9]*") &&
                speedTextField.getText().matches("[0-9]*")) {

            cmdCtrl.clear();
            cmdCtrl.reset();

            if (optimalSpeedBox.isSelected()) {
                if (productIDChoiceBox.getValue().equals("0")) {
                    cmdCtrl.setSpeed(100);
                } else if (productIDChoiceBox.getValue().equals("1")) {
                    cmdCtrl.setSpeed(50);
                } else if (productIDChoiceBox.getValue().equals("2")) {
                    cmdCtrl.setSpeed(50);
                } else if (productIDChoiceBox.getValue().equals("3")) {
                    cmdCtrl.setSpeed(200);
                } else if (productIDChoiceBox.getValue().equals("4")) {
                    cmdCtrl.setSpeed(25);
                } else if (productIDChoiceBox.getValue().equals("5")) {
                    cmdCtrl.setSpeed(25);
                }
            } else if (!optimalSpeedBox.isSelected()){
                cmdCtrl.setSpeed(Float.parseFloat(speedTextField.getText()));
            }

            batchCtrl.setAmountToProduce(Float.parseFloat(amountToProduceTextField.getText()));
            batchCtrl.setProductType(Float.parseFloat(productIDChoiceBox.getValue().toString()));
            setCompanyLabel.setText(companyTextField.getText());

            amountToProduceTextField.clear();
            speedTextField.clear();
            invalidInputLabel.setDisable(true);
            invalidInputLabel.setVisible(false);

        } else {
            //Label der siger udfyld
            invalidInputLabel.setDisable(false);
            invalidInputLabel.setVisible(true);
            invalidInputLabel.setText("Invalid input!");
            System.out.println("CHANGE: Something is not correct");

            amountToProduceTextField.clear();
            speedTextField.clear();
        }
    }

    @FXML
    public void clearFieldOnAction(ActionEvent actionEvent) {
        amountToProduceTextField.clear();
        speedTextField.clear();
    }

    private void setProductTypeLabel() {
        Float productType = batchCtrl.getProductType();
        if (productType == 0) {
            productTypeLabel.setText("Pilsner (0)");
        } else if (productType == 1) {
            productTypeLabel.setText("Wheat (1)");
        } else if (productType == 2) {
            productTypeLabel.setText("IPA (2)");
        } else if (productType == 3) {
            productTypeLabel.setText("Stout (3)");
        } else if (productType == 4) {
            productTypeLabel.setText("ALE (4)");
        } else if (productType == 5) {
            productTypeLabel.setText("Alcohol free (5)");
        }
        System.out.println(batchCtrl.getProductType());
    }

    private void incrementTime() {
        localTime = localTime.plusSeconds(1);
        timeOnLabel.setText(localTime.format(dtf));
    }

    public void consumerGUI() {
        cmdCtrl.reset();

        Consumer<String> producedAmountUpdate = text -> Platform.runLater(() -> producedLabel.setText(text));
        Consumer<String> humidityUpdate = text -> Platform.runLater(() -> humidityLabel.setText(text));
        Consumer<String> vibrationUpdate = text -> Platform.runLater(() -> vibrationLabel.setText(text));
        Consumer<String> temperatureUpdate = text -> Platform.runLater(() -> tempLabel.setText(text));
        Consumer<String> defectedUpdate = text -> Platform.runLater(() -> defectiveLabel.setText(text));
        Consumer<String> acceptedUpdate = text -> Platform.runLater(() -> acceptedLabel.setText(text));

        subscribe.setConsumer(producedAmountUpdate, subscribe.producedAmount);
        subscribe.setConsumer(humidityUpdate, subscribe.humidity);
        subscribe.setConsumer(vibrationUpdate, subscribe.vibration);
        subscribe.setConsumer(temperatureUpdate, subscribe.temperature);
        subscribe.setConsumer(defectedUpdate, subscribe.defectiveProducts);
        subscribe.setConsumer(acceptedUpdate, subscribe.acceptedProducts);

        subscribe.subscribe();
    }

    private void fillComboBox() {
        String productTypes[] = {"0", "1", "2", "3", "4", "5"};
        productIDChoiceBox.getItems().addAll(productTypes);
    }

    @FXML
    private void oeeOnAction(ActionEvent event) {
        if (tabelViewBR.getSelectionModel().getSelectedItem() != null) {
            batchReport = (BatchReport) tabelViewBR.getSelectionModel().getSelectedItem();

            setoeeLabel.setText(Integer.toString(oee.createOEE(batchReport.getBatchid())));
        } else {
            batchReportInvalid.setText("Please select a batch to get OEE");
            batchReportInvalid.setDisable(false);
            batchReportInvalid.setVisible(true);
            System.out.println("Please select a batch");
        }

    }

    @FXML
    public void selectOnAction(ActionEvent event) {

        if (userManagementTable.getSelectionModel().getSelectedItem() != null) {
            user = (User) userManagementTable.getSelectionModel().getSelectedItem();

            updateUserIDLabel.setText(Integer.toString(user.getUserID()));
            updateUsernameTextField.setText(user.getUsername());
            updatePasswordTextField.setText(user.getPassword());
            updateEmailTextField.setText(user.getEmail());

            if (user.getRole().equals("Worker")) {
                updateRWorkerRB.setSelected(true);
            } else if (user.getRole().equals("Manager")) {
                updateRManagerRB.setSelected(true);
            } else if (user.getRole().equals("Guest")) {
                updateRGuestRB.setSelected(true);
            }

        } else {
            System.out.println("GUI.Startview: error");
        }
    }

    @FXML
    public void updateUserOnAction(ActionEvent event) {
        if (updateRManagerRB.isSelected()) {
            createUserService.updateUser(Integer.parseInt(updateUserIDLabel.getText()), updateUsernameTextField.getText(), updatePasswordTextField.getText(),
                    updateEmailTextField.getText(), updateRManagerRB.getText());
        } else if (updateRWorkerRB.isSelected()) {
            createUserService.updateUser(Integer.parseInt(updateUserIDLabel.getText()), updateUsernameTextField.getText(), updatePasswordTextField.getText(),
                    updateEmailTextField.getText(), updateRWorkerRB.getText());
        } else if (updateRGuestRB.isSelected()) {
            createUserService.updateUser(Integer.parseInt(updateUserIDLabel.getText()), updateUsernameTextField.getText(), updatePasswordTextField.getText(),
                    updateEmailTextField.getText(), updateRGuestRB.getText());
        }

        tableViewUser();
    }
    @FXML
    public void UpdateColumnUserOnAction(ActionEvent event) {
        tableViewUser();
    }

    @FXML
    public void deleteUseOnAction(ActionEvent event) {
        if (userManagementTable.getSelectionModel().getSelectedItem() != null) {
            user = (User) userManagementTable.getSelectionModel().getSelectedItem();
            createUserService.deleteUserinDM(user.getUserID());
            tableViewUser();
        } else {
            System.out.println("GUI: TabelView NULL");
        }

    }
}
