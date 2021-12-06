package com.example.sempro;

import domain.StopReason;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import domain.CommandController;
import domain.BatchController;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import javafx.animation.AnimationTimer;

import java.awt.*;
import java.awt.event.*;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import java.lang.Thread;

import domain.BatchController;

import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.time.StopWatch;

import java.lang.Object;


public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    private StopReason stopReason;
    int amount = 5000;
    int defective;
    private DateTimeFormatter dtf;
    private StopWatch sw;
    private Timer timer;
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

    private boolean maintenanceCheck = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cmdCtrl = new CommandController();
        this.batchCtrl = new BatchController();
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse("00:00:00");
        sw = new StopWatch();
        timeLine = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeLine.setCycleCount(Animation.INDEFINITE);
        //setTimeOnLabel();
        stopReason = new StopReason();
        //maintenanceChecker();
        maintenance();
    }

    @FXML
    public void onStartClick() {

        try {
           /* cmdCtrl.clear();
            cmdCtrl.reset();
            batchCtrl.setAmountToProduce(amount);
            cmdCtrl.setSpeed(300);
            setSpeedLabel();
            batchCtrl.setBatchId(11);
            setBatchLabel();
            setAmountCurrentBatchLabel();
            Thread.sleep(4000);*/
            newTimer();
            setSpeedLabel();
            setBatchLabel();
            setAmountCurrentBatchLabel();
            setProductTypeLabel();
            cmdCtrl.start();
            startTimeLabel.setText(dtf.format(java.time.LocalTime.now()));

            producedLabel.setText("0");
            amountCurrentBatchLabel.setText("0");
            productCounter();

            localTime = LocalTime.parse("00:00:00");
            timeOnLabel.setText(localTime.format(dtf));
            timeLine.play();
            startBtn.setDisable(true);


            //setDefectiveLabel();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onStopClick(ActionEvent event) {
        cmdCtrl.stop();
        timer.cancel();

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

    public void productCounter() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (batchCtrl.getAmountProduced() != amount) {
                    Platform.runLater(() -> producedLabel.setText(Integer.toString(batchCtrl.getAmountProduced())));
                } else {
                    timer.cancel();
                }
            }

        }, 1, amount);
    }

    public void maintenance() {
        Timer test = new Timer();
        test.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (stopReason.stopReason() == "10" ||
                        stopReason.stopReason() == "11" ||
                        stopReason.stopReason() == "12" ||
                        stopReason.stopReason() == "13" ||
                        stopReason.stopReason() == "14") {
                    System.out.println("Stop reason: " + stopReason.stopReason());
                }
            }
        }, 1, 100000);
    }

    public void setAmountCurrentBatchLabel() {
        amountCurrentBatchLabel.setText(batchCtrl.getAmountToProduce().toString());
    }




/*    public void setDefectiveLabel() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> defectiveLabel.setText("Defective: " + batchCtrl.getDefective()));
            }
        },1, defective);

    }*/

    public void setHumidityLabel() {

    }

    public void setSpeedLabel() {
        speedLabel.setText(cmdCtrl.getSpeed().toString());
    }

    public void setBatchLabel() {
        batchLabel.setText(batchCtrl.getBatchId().toString());
    }

    @FXML
    public void changeOnAction(ActionEvent actionEvent) {
        cmdCtrl.clear();
        cmdCtrl.reset();

        if (!amountToProduceTextField.getText().isEmpty() &&
                !productIDTextField.getText().isEmpty() &&
                !speedTextField.getText().isEmpty()) {
            batchCtrl.setAmountToProduce(Float.parseFloat(amountToProduceTextField.getText()));
            batchCtrl.setProductType(Float.parseFloat(productIDTextField.getText()));
            cmdCtrl.setSpeed(Float.parseFloat(speedTextField.getText()));

            amountToProduceTextField.clear();
            productIDTextField.clear();
            speedTextField.clear();
        } else {
            //Label der siger udfyld
        }

    }

    @FXML
    public void clearFieldOnAction(ActionEvent actionEvent) {
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

    private void newTimer() {
        timer = new Timer();
    }

    private void incrementTime() {
        localTime = localTime.plusSeconds(1);
        timeOnLabel.setText(localTime.format(dtf));
    }

    private void maintenanceChecker() {
        while (true) {

            if (stopReason.stopReason() == "10") {
                System.out.println("Maintenance - stop reason ID:" + stopReason.stopReason());
                break;
            } else if (stopReason.stopReason() == "11") {
                System.out.println("Maintenance - stop reason ID:" + stopReason.stopReason());
                break;
            } else if (stopReason.stopReason() == "12") {
                System.out.println("Maintenance - stop reason ID:" + stopReason.stopReason());
                break;
            } else if (stopReason.stopReason() == "13") {
                System.out.println("Maintenance - stop reason ID:" + stopReason.stopReason());
                break;
            } else if (stopReason.stopReason() == "14") {
                System.out.println("Maintenance - stop reason ID:" + stopReason.stopReason());
                break;
            }

            continue;
        }

    }

}
