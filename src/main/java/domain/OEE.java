package domain;

import database.BatchReportDB;

public class OEE {

    private BatchReportDB batchReportDB;

    /*
        OEE = (Good Count * Ideal Cycle Time) / Planned Production Time

        Good Count = Total Count - Rejected Count
        Ideal Cycle Time = "... is the theoretical minimum time to produce one piece.", in our case: speed
        Planned Production Time = 8 hours = 28800 seconds
     */

    public OEE() {
        batchReportDB = new BatchReportDB();
    }

    public int getTotalCount(int batchID) {
        return batchReportDB.getProducedAmount(batchID);
    }

    public int getRejectedCount(int batchID) {
        return batchReportDB.getDefective(batchID);
    }

    public int getIdealCycleTime(int batchID) {
        return batchReportDB.getSpeed(batchID);
    }

    public int createOEE(int batchID) {
        int goodCount = getTotalCount(batchID) - getRejectedCount(batchID);
        int idealCycleTime = getIdealCycleTime(batchID);
        int plannedProductionTime = 28800;

        int valueOEE = (goodCount * idealCycleTime) / plannedProductionTime;

        return valueOEE;
    }
}
