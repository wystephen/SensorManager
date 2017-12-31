package pers.steve.Sensors;

import java.util.concurrent.SynchronousQueue;

public abstract class SensorAbstract<DataElementType> {
    private final java.util.concurrent.SynchronousQueue<DataElementType> data_queue = new SynchronousQueue<DataElementType>();

    private Thread SensorDataAccepter;

//    protected SensorAbstract(Thread sensorDataAccepter) {
//        SensorDataAccepter = sensorDataAccepter;
//    }


    /**
     * Start sensor means set up a new thread for reading
     * @param State
     * @return
     */
    public abstract  boolean StartSensor(int State);

    /**
     *
     * @param State
     * @return
     */
    public abstract  boolean StopSensor(int State);


}
