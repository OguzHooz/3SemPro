package com.example.sempro;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

import java.net.URL;
import java.util.*;
import java.lang.Thread;


public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    int amount = 5000;
    int defective;
    /**
     * tag input fra textfield, speed, product id, batch id,
     */
    private Timer timer;

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
    private Label timeLabel;

    @FXML
    private Label vibrationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cmdCtrl = new CommandController();
        this.batchCtrl = new BatchController();
        timer = new Timer();
    }

    @FXML
    public void onStartClick() {

        try {
            cmdCtrl.clear();
            cmdCtrl.reset();
            batchCtrl.setAmountToProduce(amount);
            cmdCtrl.setSpeed(300);
            setSpeedLabel();
            batchCtrl.setBatchId(11);
            setBatchLabel();
            setAmountCurrentBatchLabel();
            Thread.sleep(4000);

            cmdCtrl.start();

            productCounter();


            //setDefectiveLabel();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onStopClick(ActionEvent event) {
        cmdCtrl.stop();
        timer.cancel();
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

    public void setSpeedLabel() {
        speedLabel.setText(cmdCtrl.getSpeed().toString());
    }

    public void setBatchLabel() {
        batchLabel.setText(batchCtrl.getBatchId() + "");
    }

    @FXML
    public void changeOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void clearFieldOnAction(ActionEvent actionEvent) {
    }
}
