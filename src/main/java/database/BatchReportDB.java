package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class BatchReportDB {

    private DatabaseConnection dbConnection;
    private Connection connection;
    private ResultSet rs;

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

    public int getBatchID() {
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM batchreport";
            rs = st.getGeneratedKeys();
            rs =  st.executeQuery(sql);

            int batchid = 0;
            while (rs.next()) {
                batchid = rs.getInt("batchid");
            }
            st.close();
            return batchid;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

}
