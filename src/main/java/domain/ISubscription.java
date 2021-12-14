package domain;

import java.util.function.Consumer;

public interface ISubscription {

    public void subscribe();

    public void setConsumer(Consumer<String> consumer, String nodeName);

    public final static String batchID = "BatchID";
    public final static String amountToProduce = "Amount to Produce";
    public final static String producedAmount = "Produced Amount";
    public final static String acceptedProducts = "Accepted Products";
    public final static String defectiveProducts = "Defective Products";


    public final static String temperature = "Temperature";
    public final static String humidity = "Humidity";
    public final static String vibration = "Vibration";


    //States
    public final static String deactivated = "Deactivated: 0";
    public final static String clearing = "Clearing: 1";
    public final static String stopped = "Stopped: 2";
    public final static String starting = "Starting: 3";
    public final static String idle = "Idle: 4";
    public final static String suspended = "Suspended: 5";
    public final static String execute = "Execute: 6";
    public final static String stopping = "Stopping: 7";
    public final static String aborting = "Aborting: 8";
    public final static String aborted = "Aborted: 9";
    public final static String holding = "Holding: 10";
    public final static String held = "Held: 11";
    public final static String resetting = "Resetting: 15";
    public final static String completing = "Completing: 16";
    public final static String complete = "Complete: 17";
    public final static String deactivating = "Deactivating: 18";
    public final static String activating = "Activating: 19";





}
