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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import domain.BatchController;
import domain.BatchReport;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.time.StopWatch;


public class StartView implements Initializable {

    private CommandController cmdCtrl;
    private BatchController batchCtrl;
    private StopReason stopReason;
    int run = 500;
    private DateTimeFormatter dtf;
    private StopWatch sw;
    private Timer timer;
    private int seconds = 0;
    private int minutes = 0;

    private TimerTask timerTask;

    private Timeline timeLine;
    private LocalTime localTime;
    private  BatchReport batchReport;


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
    private Button downloadReportBtn;

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
    private Label companyBRLabel;

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
    private TableView<BatchReport> tabelViewBR;
    @FXML
    private TableColumn <BatchReport,String> companyColumn;

    @FXML
    private TableColumn <BatchReport,Integer> batchidColumn;

    @FXML
    private TableColumn  <BatchReport,Integer> amountproducedColumn;

    @FXML
    private TableColumn <BatchReport,String> amounttoproduceColumn;

    @FXML
    private TableColumn <BatchReport,String> productTypeColumn;

    @FXML
    private TableColumn <BatchReport,Integer>speedColumn;

    @FXML
    private TableColumn <BatchReport,Integer> acceptedColumn;

    @FXML
    private TableColumn <BatchReport,Integer> defectedColumn;

    @FXML
    private TableColumn<BatchReport,String> IdletimeColumn;

    @FXML
    private TableColumn <BatchReport,String> timeonColumn;

    @FXML
    private TableColumn <BatchReport,String> starttimeColumn;


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
        tableView();
    }
    public  void tableView(){
        batchReport = new BatchReport();
        columns();
        tabelViewBR.setItems(batchReport.getInformationBR());


    }

    public void columns(){
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

     /*   tabelViewBR.getColumns().addAll(companyColumn,batchidColumn,amountproducedColumn,
               amounttoproduceColumn,productTypeColumn,speedColumn,acceptedColumn,defectedColumn,IdletimeColumn
               ,timeonColumn,starttimeColumn);*/

    }

    @FXML
    public void onStartClick() {

        try {
            newTimer();
            cmdCtrl.start();
            startTimeLabel.setText(dtf.format(java.time.LocalTime.now()));


            localTime = LocalTime.parse("00:00:00");
            timeOnLabel.setText(localTime.format(dtf));
            timeLine.play();
            startBtn.setDisable(true);

            //Thread.sleep(5000);
            productCounter();
//            updateDefective();
//            updateAccepted();
//            updateHumidity();
//            updateTemperature();

            setSpeedLabel();
            setBatchLabel();
            setAmountCurrentBatchLabel();
            setProductTypeLabel();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onStopClick(ActionEvent event) {
        cmdCtrl.stop();
        timer.cancel();
        cmdCtrl.reset();
        cmdCtrl.clear();

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
        amountToProduceTextField.clear();
        productIDTextField.clear();
        speedTextField.clear();
    }

    public void productCounter() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (batchCtrl.getAmountProduced() != run) {
                    Platform.runLater(() -> producedLabel.setText(Integer.toString(batchCtrl.getAmountProduced())));
                } else {
                    timer.cancel();
                }
            }

        }, 1, run);

    }

    public void setAmountCurrentBatchLabel() {
        amountCurrentBatchLabel.setText(batchCtrl.getAmountToProduce().toString());
    }

    /*public void updateDefective() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> defectiveLabel.setText("Defective: " + batchCtrl.getDefective()));
            }
        },1, run);

    }

    public void updateAccepted() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> acceptedLabel.setText("Accepted: " + (batchCtrl.getAmountProduced() - batchCtrl.getDefective())));
            }
        },1, run);

    }*/

    /*public void updateHumidity() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> humidityLabel.setText("Humidity: " + batchCtrl.getHumidity().toString()));
            }
        },1, run);

    }

    public void updateTemperature() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> tempLabel.setText("Temperature: " + batchCtrl.getTemperature()));
            }
        },1, run);

    }*/

    /*public void updateVibration() {
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Platform.runLater(() -> tempLabel.setText("Temperature: " + batchCtrl.getVibration()));
            }
        },1, run);

    }*/

    public void setSpeedLabel() {
        speedLabel.setText(cmdCtrl.getSpeed().toString());
    }

    public void setBatchLabel() {
        batchLabel.setText(batchCtrl.getBatchId().toString());
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

    public void SetBatchReport(){
//        amountProducedBRLabel.setText(cmdCtrl.);
//        speedBRLabel.setText(cmdCtrl.getSpeed().toString());
//        acceptedBRLabel.setText();

    }

}
