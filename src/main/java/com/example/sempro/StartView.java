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
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.lang.Thread;

public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    private StopReason stopReason;
    int run = 500;
    private DateTimeFormatter dtf;
    private int seconds = 0;
    private int minutes = 0;

    private TimerTask timerTask;

    private Timeline timeLine;
    private LocalTime localTime;

    /**
     * tag input fra textfield, speed, product id, batch id,
     */

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

    private ISubscription subscribe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cmdCtrl = new CommandController();
        this.batchCtrl = new BatchController();
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse("00:00:00");
        timeLine = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeLine.setCycleCount(Animation.INDEFINITE);
        //setTimeOnLabel();
        stopReason = new StopReason();
        this.subscribe = new Subscription();
        consumerGUI();
    }

    @FXML
    public void onStartClick() {

        try {
            speedLabel.setText(cmdCtrl.getSpeed().toString());
            batchLabel.setText(batchCtrl.getBatchId().toString());
            amountCurrentBatchLabel.setText(batchCtrl.getAmountToProduce().toString());
            setProductTypeLabel();

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
        tempLabel.setText("0");
        batchLabel.setText("0");
        producedLabel.setText("0");
        humidityLabel.setText("0");
        amountBatchLabel.setText("0");
        acceptedLabel.setText("0");
        vibrationLabel.setText("0");
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

        subscribe.setConsumer(producedAmountUpdate, subscribe.producedAmount);

        subscribe.subscribe();
    }

}
