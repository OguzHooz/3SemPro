package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchReport {

    private DatabaseConnection dbConnection;
    private Connection connection;

    public BatchReport() {
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

}
