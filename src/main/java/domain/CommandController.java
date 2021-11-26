package domain;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class CommandController {

    private MachineConnection machineConnection;

    public CommandController() {
        this.machineConnection = new MachineConnection("127.0.0.1", 4840);
        this.machineConnection.connect();
    }

    public void reset() {
        try {

            NodeId reset = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            machineConnection.getClient().writeValue(reset, DataValue.valueOnly(new Variant(1))).get();
            System.out.println("domain.Write: RESET");

            cmdChangeRequest();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        try {

            NodeId start = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            machineConnection.getClient().writeValue(start, DataValue.valueOnly(new Variant(2))).get();
            System.out.println("domain.Write: START");

            cmdChangeRequest();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        try {

            NodeId stop = new NodeId(6, "=::Program:Cube.Command.CntrlCmd");
            machineConnection.getClient().writeValue(stop, DataValue.valueOnly(new Variant(3))).get();
            System.out.println("domain.Write: STOP");

            cmdChangeRequest();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void abort() {
        try {

            NodeId abort = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            machineConnection.getClient().writeValue(abort, DataValue.valueOnly(new Variant(4))).get();
            System.out.println("domain.Write: ABORT");

            cmdChangeRequest();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        try {

            NodeId clear = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            machineConnection.getClient().writeValue(clear, DataValue.valueOnly(new Variant(5))).get();
            System.out.println("domain.Write: CLEAR");

            cmdChangeRequest();

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void cmdChangeRequest() {
        try {

            NodeId nodeId6 = NodeId.parse("ns=6;s=::Program:Cube.Command.CmdChangeRequest");
            machineConnection.getClient().writeValue(nodeId6, DataValue.valueOnly(new Variant(true))).get();
            System.out.println("domain.Write: CmdChangeRequest: true");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void setSpeed(float speed) {
        try {

            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.MachSpeed");
            machineConnection.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant((float) speed))).get();
            System.out.println("domain.Write: Speed: " + speed);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public int getSpeed() {
        int value = 0;
        try {

            while(true) {
                NodeId nodeId5 = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
                DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId5)
                        .get();

                Variant variant = dataValue.getValue();

                value = (int) variant.getValue();

                System.out.println("Produced: " + value);
                return value;
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }

    }
}
