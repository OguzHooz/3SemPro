package domain;

import database.BatchReportDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BatchReport {
   private int batchid;
    private String company;
    private int amountProduced;
    private int amountToProduce;
    private String productType;
    private int speed;
    private int accepted;
    private int defected;
    private String idleTime;
    private String timeOn;
    private String startTime;
    private BatchReportDB batchReportDB;


    public BatchReport(String company, int amountProduced, int amountToProduce, String productType, int speed, int accepted,
                       int defected, String idleTime, String timeOn, String startTime) {
        this.company = company;
        this.amountProduced = amountProduced;
        this.amountToProduce = amountToProduce;
        this.productType = productType;
        this.speed = speed;
        this.accepted = accepted;
        this.defected = defected;
        this.idleTime = idleTime;
        this.timeOn = timeOn;
        this.startTime = startTime;
    }

    public BatchReport(String company,int batchid, int amountProduced, int amountToProduce, String productType, int speed, int accepted,
                       int defected, String idleTime, String timeOn, String startTime) {
        this.company = company;
        this.batchid=batchid;
        this.amountProduced = amountProduced;
        this.amountToProduce = amountToProduce;
        this.productType = productType;
        this.speed = speed;
        this.accepted = accepted;
        this.defected = defected;
        this.idleTime = idleTime;
        this.timeOn = timeOn;
        this.startTime = startTime;
    }

    public BatchReport() {
        this.batchReportDB = new BatchReportDB();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAmountProduced() {
        return amountProduced;
    }

    public void setAmountProduced(int amountProduced) {
        this.amountProduced = amountProduced;
    }

    public int getAmountToProduce() {
        return amountToProduce;
    }

    public void setAmountToProduce(int amountToProduce) {
        this.amountToProduce = amountToProduce;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getDefected() {
        return defected;
    }

    public void setDefected(int defected) {
        this.defected = defected;
    }

    public String getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(String idleTime) {
        this.idleTime = idleTime;
    }

    public String getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(String timeOn) {
        this.timeOn = timeOn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getBatchID() {
        batchReportDB = new BatchReportDB();
        return batchReportDB.getBatchID();
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

        public void BatchReportDM(String company, int amountProduced, int amountToProduce, String productType, int speed,
                              int accepted, int defected, String idleTime, String timeOn, String startTime) {
      batchReportDB.createBatchReport( company, amountProduced,  amountToProduce, productType, speed,
        accepted, defected,  idleTime,  timeOn,  startTime);
    }
    public ObservableList getInformationBR(){
        ObservableList<BatchReport> OBlist;
        OBlist = FXCollections.observableArrayList(batchReportDB.getReportInfo());
        return OBlist;

    }
    public void deleteeReportinDM(int batchID){
        batchReportDB = new BatchReportDB();
        batchReportDB.deleteaReportinDB(batchID);
    }
}
