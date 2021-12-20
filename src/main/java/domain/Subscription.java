
package domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Subscription implements ISubscription {

    private static MachineConnection machineConnection;
    private Map<String, Consumer<String>> consumerMap;
    private static final AtomicLong ATOMICLONG = new AtomicLong(1L);

    //Production
    private final NodeId productCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
    private final NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
    private final NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
    private final NodeId temperatureNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
    private final NodeId defectedNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");

    private float totalProducedValue;
    private float totalAcceptedValue;
    private float totalDefectedValue;
    private float humidityValue;
    private float vibrationValue;
    private float temperatureValue;

    public Subscription() {
        //Simulation
        //machineConnection = new MachineConnection("127.0.0.1", 4840);
        //Machine
        this.machineConnection = new MachineConnection("192.168.0.122", 4840);
        machineConnection.connect();
        consumerMap = new HashMap();
    }

    private static void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        Variant variant = value.getValue();
        int val = (int) variant.getValue();
        System.out.println("subscription value received: item=" + item.getReadValueId().getNodeId() + ", value=" + val);
    }

    public void subscribe() {
        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(readValueId(productCountNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(humidityNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(vibrationNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(temperatureNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(defectedNode), MonitoringMode.Reporting, monitoringParameters()));

        Consumer<DataValue> productCountItem = (dataValue) -> startConsumer(producedAmount, dataValue);
        Consumer<DataValue> humidityItem = (dataValue) -> startConsumer(humidity, dataValue);
        Consumer<DataValue> vibrationItem = (dataValue) -> startConsumer(vibration, dataValue);
        Consumer<DataValue> temperatureItem = (dataValue) -> startConsumer(temperature, dataValue);
        Consumer<DataValue> defectedItem = (dataValue) -> startConsumer(defectiveProducts, dataValue);


        try {
            UaSubscription subscription = machineConnection.getClient().getSubscriptionManager().createSubscription(10.0).get();
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, requestList).get();

            items.get(0).setValueConsumer(productCountItem);
            items.get(1).setValueConsumer(humidityItem);
            items.get(2).setValueConsumer(vibrationItem);
            items.get(3).setValueConsumer(temperatureItem);
            items.get(4).setValueConsumer(defectedItem);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setConsumer(Consumer<String> consumer, String nodeName) {
        consumerMap.put(nodeName, consumer);
    }

    private void startConsumer(String nodeName, DataValue dataValue) {
        consumerMap.get(nodeName).accept(dataValue.getValue().getValue().toString());
        
        switch (nodeName) {
            case producedAmount:
                this.totalProducedValue = Float.parseFloat(dataValue.getValue().getValue().toString());
                break;
            case humidity:
                this.humidityValue = Float.parseFloat(dataValue.getValue().getValue().toString());
                break;
            case vibration:
                this.vibrationValue = Float.parseFloat(dataValue.getValue().getValue().toString());
            case temperature:
                this.temperatureValue = Float.parseFloat(dataValue.getValue().getValue().toString());
            case defectiveProducts:
                this.totalDefectedValue = Float.parseFloat(dataValue.getValue().getValue().toString());
                break;
            case acceptedProducts:
                this.totalAcceptedValue = this.totalProducedValue - this.totalDefectedValue;
                break;
            default:
        }
    }

        private MonitoringParameters monitoringParameters() {
            return new MonitoringParameters(
                    Unsigned.uint(ATOMICLONG.getAndIncrement()),
                    10.0, // sampling interval
                    null, // filter, null means use default
                    Unsigned.uint(1), // queue size
                    true // discard oldest
            );
        }

        private ReadValueId readValueId(NodeId name) {
            return new ReadValueId(name, AttributeId.Value.uid(), null, null);
        }

}

