package pers.steve.Sensors;

public class SensorOriginalStringEvent extends SensorDataEvent {

    SensorOriginalStringEvent(Object source, byte[] b_data) {
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
