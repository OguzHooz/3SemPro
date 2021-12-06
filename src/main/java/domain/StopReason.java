package domain;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class StopReason {

    private MachineConnection machineConnection;

    public void CommandController() {
        this.machineConnection = new MachineConnection("127.0.0.1", 4840);
        this.machineConnection.connect();
    }

    public Object stopReason() {
        Object value = 0;
        try {

            NodeId nodeId = new NodeId(6, "::Program:Cube.Admin.StopReasonId");
            DataValue dataValue = machineConnection.getClient().readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            Variant variant = dataValue.getValue();

            value = variant.getValue();
            System.out.println("domain.Read: Stop Reason: " + value);
            return value;

        } catch (Throwable ex) {
            ex.printStackTrace();
            return value;
        }

    }


}
