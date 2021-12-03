package domain;

import database.DatabaseConnection;
import java.sql.Connection;

public class Main {

    static DatabaseConnection dbConnection;
    static Connection connection;

    public static void main(String[] args) {
        BatchController batch = new BatchController();
        CommandController cmd = new CommandController();
        float amount = 65535;

        try {

            /*cmd.clear();
            cmd.reset();
            Thread.sleep(8000);
            batch.setProductType(2);
            cmd.setSpeed(600);
            batch.setBatchId(1);
            batch.setAmountToProduce(10000);
            Thread.sleep(2000);
            cmd.start();*/
            //Thread.sleep(2000);

            //batch.getBatchId();
            //batch.getBatchAmount();
            //cmd.start();
/*
            batch.getBatchId();
            batch.getProductType();
            batch.getAmountToProduce();
            batch.getAmountProduced();
            batch.getDefective();*/

            dbConnection = new DatabaseConnection();
            connection = DatabaseConnection.getConnection();

            DatabaseConnection.getConnection();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

}
