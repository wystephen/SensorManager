package pers.steve.Sensors;

public abstract class SensorIMU<DataInterfere>
        extends SensorAbstract<IMUDataElement, DataInterfere>
        implements ISensor {


    public SensorIMU() {
        setSensorName("GeneralIMU");
    }



}
