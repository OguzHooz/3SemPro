package database;

import domain.BatchReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchReportDB {

    private DatabaseConnection dbConnection;
    private Connection connection;
     private BatchReport batchReport;

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

    public ObservableList<BatchReport> getReportInfo() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM batchreport");
            while (rs.next()) {
                String company = rs.getString("company");
                int batchid = rs.getInt("batchid");
                int amountproduced = rs.getInt("amountproduced");
                int amounttoproduce = rs.getInt("amounttoproduce");
                String productType=rs.getString("producttype");
                int speed=rs.getInt("speed");
                int accepted=rs.getInt("accepted");
                int detected=rs.getInt("defected");
                String idletime=rs.getString("idletime");
                String timeon=rs.getString("timeon");
                String starttime=rs.getString("starttime");

                 batchReport = new BatchReport(company,batchid,amountproduced,amounttoproduce,
                        productType,speed,accepted,detected,idletime,timeon,starttime);
                ObservableList<BatchReport> brList = FXCollections.observableArrayList();
                brList.add(batchReport);
                return brList;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;

    }

}
