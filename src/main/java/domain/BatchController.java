package domain;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class BatchController {

    private MachineConnection machineConnection;

    public BatchController() {
        //Simulation
        //this.machineConnection = new MachineConnection("127.0.0.1", 4840);
        //Machine
        this.machineConnection = new MachineConnection("192.168.0.122", 4840);
        this.machineConnection.connect();
    }

    // Antal produceret (amountProduced)
    public int getAmountProduced() {
        int value = 0;
        try {

                NodeId nodeId5 = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
                DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId5)
                        .get();

                Variant variant = dataValue.getValue();

                value = (int) variant.getValue();
            System.out.println("Amount produced: " + value);
                return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    public Object getAmountToProduce() {
        Object value = 0;
        try {

                NodeId nodeId5 = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
                DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId5)
                        .get();

                Variant variant = dataValue.getValue();

                value = variant.getValue();
                System.out.println("amount to produce " + value);
                return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    //Antal der skal produceres (amountToProduce)
    public void setAmountToProduce(float amount) {
        try {

            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
            machineConnection.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(amount))).get();
            System.out.println("domain.Write, Set product amount: " + amount);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public Object getBatchId() {
        Object value = 0;
        try {

                NodeId batchId = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
                DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, batchId)
                        .get();

                Variant variant = dataValue.getValue();

                value = variant.getValue();

                System.out.println("Batch ID: " + value);
                return value;


        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    public void setBatchId(float id) {
        try {

            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
            machineConnection.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(id))).get();
            System.out.println("domain.write, set batch ID to: " + id);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public float getProductType() {
        float type = 0;
        try {

            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            type = (float) variant.getValue();

            System.out.println("domain.read, get product type: " + type);
            return type;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return type;
        }
    }

    public void setProductType(float id) {
        try {

            NodeId nodeId5 = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
            machineConnection.getClient().writeValue(nodeId5, DataValue.valueOnly(new Variant(id))).get();
            System.out.println("domain.Write: Set product type: " + id);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


    public float getDefective() {
        float value = 0;
        try {

            NodeId nodeId = new NodeId(1, "::Program:Cube.Admin.ProdDefectiveCount");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            value = (float) variant.getValue();
            System.out.println("domain.Read: Defective: " + value);
            return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    public Object getHumidity() {
        Object value = 0;
        try {

            NodeId nodeId = new NodeId(1, "::Program:Cube.Status.Parameter[2]");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            value = variant.getValue();
            System.out.println("domain.Read: Humidity: " + value);
            return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    public float getTemperature() {
        float value = 0;
        try {

            NodeId nodeId = new NodeId(1, "::Program:Cube.Status.Parameter[3]");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            value = (float) variant.getValue();
            System.out.println("domain.Read: Temperature: " + value);
            return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }

    public float getVibration() {
        float value = 0;
        try {

            NodeId nodeId = new NodeId(1, "::Program:Cube.Status.Parameter[4]");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            value = (float) variant.getValue();
            System.out.println("domain.Read: Vibration: " + value);
            return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }
    }


}
