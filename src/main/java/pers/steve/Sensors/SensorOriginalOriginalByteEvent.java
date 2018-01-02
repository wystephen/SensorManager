package pers.steve.Sensors;

public class SensorOriginalOriginalByteEvent extends SensorOriginalDataEvent {

    SensorOriginalOriginalByteEvent(Object source, byte[] b_data) {
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
