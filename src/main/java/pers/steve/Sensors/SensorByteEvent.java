package pers.steve.Sensors;

public class SensorByteEvent extends SensorDataEvent {

    SensorByteEvent(Object source, byte[] b_data) {
        super(source, b_data);
    }

    /**
     *
     * @return data from sensor as byte[].
     */
    public byte[] getByteData(){
        return super.b_data;
    }
}
