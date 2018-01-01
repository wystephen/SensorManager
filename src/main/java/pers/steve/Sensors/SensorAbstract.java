package pers.steve.Sensors;

import java.util.concurrent.SynchronousQueue;

/**
 *
 * @param <DataElementType>
 * @param <DataInterface>
 */
public abstract class SensorAbstract<DataElementType, DataInterface> {
    private java.util.concurrent.SynchronousQueue<DataElementType> data_queue = new SynchronousQueue<DataElementType>();

    private Thread SensorDataAccepter;

    private DataInterface data_interface_tool;

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String sensorName) {
        SensorName = sensorName;
    }

    private String SensorName;




}
