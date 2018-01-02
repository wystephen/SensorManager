package pers.steve.Sensors;

public class SensorJY901 extends SensorIMU<SerialInterface> {
    /**
     * Start sensor means set up a new thread for reading
     *
     * @param State
     * @return
     */
    @Override
    public boolean StartSensor(int State) {
        return false;
    }

    /**
     * @param State
     * @return
     */
    @Override
    public boolean StopSensor(int State) {
        return false;
    }


}
