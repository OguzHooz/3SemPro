package database;

import domain.BatchReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BatchReportDB {

    private  DatabaseConnection dbConnection;
    private  Connection connection;
    public BatchReportDB() {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }

    public void createBatchReport(String company, int amountProduced, int amountToProduce, String productType, int speed,
                                  int accepted, int defected, String idleTime, String timeOn, String startTime) {
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO batchreport "
                    + "(company, amountProduced, amountToProduce, productType, speed, accepted, defected, idletime,"
                    + "timeon, starttime) "
                    + "VALUES ('" + company + "'," + amountProduced + "," + amountToProduce + ",'" + productType
                    + "'," + speed + "," + accepted + "," + defected + ",'" + idleTime + "','" + timeOn + "','"
                    + startTime + "')";
            st.executeQuery(sql);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public  List getReportInfo() {
        List<BatchReport> brList  = new ArrayList<>();
        try {
           Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM batchreport");
            while (rs.next()) {
                brList.add(new BatchReport(rs.getString("company"), rs.getInt("batchid"),
                        rs.getInt("amountproduced"), rs.getInt("amounttoproduce"),
                        rs.getString("producttype"), rs.getInt("speed"),
                        rs.getInt("accepted"), rs.getInt("defected"),
                        rs.getString("idletime"), rs.getString("timeon"),
                        rs.getString("starttime")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return brList;

    }

}

            while (rs.next()) {
                batchid = rs.getInt("batchid");
            }
    public int getBatchID() {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM batchreport";
            rs = st.getGeneratedKeys();
            rs =  st.executeQuery(sql);

            int batchid = 0;
            st.close();
            return batchid;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    private ResultSet rs;