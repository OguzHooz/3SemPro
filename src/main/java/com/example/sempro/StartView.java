package com.example.sempro;

import domain.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import javax.swing.text.LabelView;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.lang.Thread;

public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    private BatchReport batchReport;
    int run = 500;
    private DateTimeFormatter dtf;
    private int seconds = 0;
    private int minutes = 0;

    private TimerTask timerTask;

    private Timeline timeLine;
    private LocalTime localTime;

    //FXML
    @FXML
    private Button abortBtn;

    @FXML
    private Label acceptedLabel;

    @FXML
    private Label amountBatchLabel;

    @FXML
    private TextField amountBatchTextField;

    @FXML
    private Label amountCurrentBatchLabel;

    @FXML
    private TextField amountToProduceTextField;

    @FXML
    private Label batchLabel;

    @FXML
    private Button changeBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button clearFieldBtn;

    @FXML
    private Button createUserBtn;

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
    private TextField productIDTextField;

    @FXML
    private Button resetBtn;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField speedTextField;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

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
    private Label maintenanceLabel;

    private ISubscription subscribe;
    private LoginController loginController;
    private String host;
    private int port;

    private static StartView instance = new StartView();
    public static StartView getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cmdCtrl = new CommandController(this.host, this.port);
        this.batchCtrl = new BatchController(this.host, this.port);
        this.batchReport = new BatchReport();
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse("00:00:00");
        timeLine = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeLine.setCycleCount(Animation.INDEFINITE);
        //setTimeOnLabel();
        this.subscribe = new Subscription(this.host, this.port);
        consumerGUI();
    }


    @FXML
    public void onStartClick() {

        try {
            speedLabel.setText(cmdCtrl.getSpeed().toString());
            batchLabel.setText(batchCtrl.getBatchId().toString());
            amountCurrentBatchLabel.setText(batchCtrl.getAmountToProduce().toString());
            setProductTypeLabel();
            //batchLabel.setText((batchReport.getBatchID() + 1) + "");

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
        cmdCtrl.stop();

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
        amountBatchLabel.setText("0");
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
    public void onExitClick(ActionEvent actionEvent) {
        System.exit(0);
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
        cmdCtrl.clear();
        cmdCtrl.reset();

        if (!amountToProduceTextField.getText().isEmpty() &&
                !productIDTextField.getText().isEmpty() &&
                !speedTextField.getText().isEmpty() &&
                amountToProduceTextField.getText().matches("[0-9]*") &&
                productIDTextField.getText().matches("[0-9]*") &&
                speedTextField.getText().matches("[0-9]*")) {

            batchCtrl.setAmountToProduce(Float.parseFloat(amountToProduceTextField.getText()));
            batchCtrl.setProductType(Float.parseFloat(productIDTextField.getText()));
            cmdCtrl.setSpeed(Float.parseFloat(speedTextField.getText()));

            amountToProduceTextField.clear();
            productIDTextField.clear();
            speedTextField.clear();

        } else {
            //Label der siger udfyld
            invalidInputLabel.setDisable(false);
            invalidInputLabel.setVisible(true);
            invalidInputLabel.setText("Invalid input!");
            System.out.println("CHANGE: Something is not correct");

            amountToProduceTextField.clear();
            productIDTextField.clear();
            speedTextField.clear();
        }
    }

    @FXML
    public void clearFieldOnAction(ActionEvent actionEvent) {
        amountToProduceTextField.clear();
        productIDTextField.clear();
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

    public void selectServer() {
        if (loginController.getSimRB().isSelected()) {
            host = "127.0.0.1";
            port = 4840;
        } else if (loginController.getMacRB().isSelected()) {
            host = "192.168.0.122";
            port = 4840;
        }
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
