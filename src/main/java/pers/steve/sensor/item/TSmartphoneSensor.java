package pers.steve.sensor.item;

public class TSmartphoneSensor extends SensorAbstract implements SensorInterface{
    @Override
    public boolean setInterface(Object data_interface_tool) {
        return false;
    }

    /**
     * Start sensor means set up a new thread for reading
     *
     * @param State
     * @return
     */
    @Override
    public boolean startSensor(int State) {
        return false;
    }

    /**
     * @param State
     * @return
     */
    @Override
    public boolean stopSensor(int State) {
        return false;
    }

    /**
     * start to save date from sensor to file.
     *
     * @param state
     * @return
     */
    @Override
    public boolean startFileOutput(int state) {
        return false;
    }

    /**
     * @param state
     * @return
     */
    @Override
    public boolean stopFileOutput(int state) {
        return false;
    }

    /**
     * @param state
     * @return
     */
    @Override
    public boolean startGUIOutput(int state) {
        return false;
    }

    /**
     * @param state
     * @return
     */
    @Override
    public boolean stopGUIOutput(int state) {
        return false;
    }

    @Override
    public boolean setGUIEventListener(SensorDataListener<? extends SensorDataElement> liste) {
        return false;
    }
}
