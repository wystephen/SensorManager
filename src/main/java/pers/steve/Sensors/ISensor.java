package pers.steve.Sensors;

public interface ISensor {
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

    /**
     * start to save date from sensor to file.
     * @param state
     * @return
     */
    public abstract boolean StartFileOutput(int state);

    /**
     *
     * @param state
     * @return
     */
    public abstract boolean StopFileOutput(int state);
}
