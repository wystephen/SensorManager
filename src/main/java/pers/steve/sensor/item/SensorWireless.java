package pers.steve.sensor.item;

/**
 * For Wireless sensors like wifi,ble, uwb.
 * @param <DataInterface>
 */
public abstract class SensorWireless<DataInterface>
        extends SensorAbstract<WirelessDataElement,DataInterface>
        implements SensorInterface {

    SensorWireless(){
        setSensorName("GeneralWireless");
    }



    @Override
    public boolean startGUIOutput(int state) {
        return false;
    }

    @Override
    public boolean stopGUIOutput(int state) {
        return false;
    }


    @Override
    public boolean startFileOutput(int state) {
        return false;
    }

    @Override
    public boolean stopFileOutput(int state) {
        return false;
    }






}
