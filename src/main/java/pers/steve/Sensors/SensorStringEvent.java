package pers.steve.Sensors;

public class SensorStringEvent extends SensorDataEvent {

    SensorStringEvent(Object source, byte[] b_data) {
        super(source, b_data);
    }

    /**
     *
     * @return data from sensor as String.
     */
    public String getStringData(){
        return new String(super.b_data);
    }
}
