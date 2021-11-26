package com.example.sempro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import domain.CommandController;
import domain.BatchController;
import javafx.scene.control.Label;

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

    @FXML
    public void onStartClick(ActionEvent event) {
        int amount = 5000;
        try {
            cmdCtrl.clear();
            cmdCtrl.reset();
            batchCtrl.setAmountToProduce(amount);
            cmdCtrl.setSpeed(600);
            Thread.sleep(4000);

            cmdCtrl.start();

            while (batchCtrl.getAmountProduced() != amount) {
                Thread.sleep(2000);
                producedLabel.setText(Integer.toString(batchCtrl.getAmountProduced()));
            }

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

}
