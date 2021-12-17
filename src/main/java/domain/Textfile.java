package domain;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;

public class Textfile {

    public Textfile() {
    }

    public void createTextfile(String company, int batchID, float amountProduced, float amountToProduce, String productType, float speed, float accepted,
                               float defected, String idleTime, String timeOn, String startTime) {
        try {
            FileWriter fw = new FileWriter("batch_report.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("Company: " + company);
            pw.println("Batch ID: " + batchID);
            pw.println("Amount produced: " + amountProduced);
            pw.println("Amount to produce: " + amountToProduce);
            pw.println("Product type: " + productType);
            pw.println("Speed: " + speed);
            pw.println("Accepted: " + accepted);
            pw.println("Defeceted: " + defected);
            pw.println("Idle time: " + idleTime);
            pw.println("Time on: " + timeOn);
            pw.println("Start time: " + startTime);
            pw.println("-------------------------------------------------------");

            pw.close();
            System.out.println("Saved into textfile");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }






}
