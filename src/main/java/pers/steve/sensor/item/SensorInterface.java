package pers.steve.sensor.item;

/**
 * Basic interface of sensor class.
 * Include the method to control the read, display gui and save to file.
 */
public interface SensorInterface {
    /**
     * Start sensor means set up a new thread for reading
     * @param State
     * @return
     */
    public abstract  boolean startSensor(int State);

    /**
     *
     * @param State
     * @return
     */
    public abstract  boolean stopSensor(int State);

    /**
     * start to save date from sensor to file.
     * @param state
     * @return
     */
    public abstract boolean startFileOutput(int state);

    /**
     *
     * @param state
     * @return
     */
    public abstract boolean stopFileOutput(int state);

    /**
     *
     * @param state
     * @return
     */
    public abstract boolean startGUIOutput(int state);

    /**
     *
     * @param state
     * @return
     */
    public abstract boolean stopGUIOutput(int state);


    public abstract boolean setGUIEventListener(SensorDataListener<? extends SensorDataElement> listener);
}
