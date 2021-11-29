package com.example.sempro;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import domain.CommandController;
import domain.BatchController;
import javafx.scene.control.Label;
import java.util.*;
import java.lang.Thread;


public class StartView {

    private CommandController cmdCtrl = new CommandController();
    private BatchController batchCtrl = new BatchController();

    //FXML
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;

    @FXML
    private Label producedLabel;

    int amount = 5000;

    @FXML
    public void onStartClick(ActionEvent event) {

        try {
            cmdCtrl.clear();
            cmdCtrl.reset();
            batchCtrl.setAmountToProduce(amount);
            cmdCtrl.setSpeed(600);
            Thread.sleep(4000);

            cmdCtrl.start();

            productCounter();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void updateProduced() {
        producedLabel.setText(Integer.toString(batchCtrl.getAmountProduced()));
    }

    @FXML
    public void onStopClick(ActionEvent event) {
        cmdCtrl.stop();
    }

    public void productCounter() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            
            public void run() {
                if (batchCtrl.getAmountProduced() != amount) {
                    Platform.runLater(() -> producedLabel.setText("Produced: " + batchCtrl.getAmountProduced()));

                } else {
                    timer.cancel();
                }
            }


        }, 0, amount);
    }


}
