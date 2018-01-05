package pers.steve.sensor.item;

/**
 * For Wireless sensors like wifi,ble, uwb.
 * @param <T>
 * @param <DataInterface>
 */
public abstract class SensorWireless<T extends WirelessDataElement,DataInterface>
        extends SensorAbstract<T,DataInterface>
        implements SensorInterface {

    private SensorDataListener<T> guiListener;
    private FileListener fileListener;


    SensorWireless(){
        setSensorName("GeneralWireless");
    }


    @Override
    public boolean setGUIEventListener(SensorDataListener<? extends SensorDataElement> listener) {
        guiListener = (SensorDataListener<T>) listener;
        return true;
    }

    @Override
    public boolean startGUIOutput(int state) {
        if (null != guiListener) {
            System.out.print("You should shop GUI Output first.");
            return false;
        }
        guiListener = new VisualListener();
        if (addDataListener(guiListener)) {

            return true;
        } else {

            return false;
        }
    }


    @Override
    public boolean stopGUIOutput(int state) {
        if (removeDataListener(guiListener)) {
            guiListener = null;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean startFileOutput(int state) {
        if (null != fileListener) {
            System.out.print("You should stop File Output First");
            return false;
        }
        fileListener = new FileListener();

        if (addDataListener(fileListener)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean stopFileOutput(int state) {
        if(removeDataListener(fileListener)){
            return true;
        }else{
            return false;
        }
    }


    class VisualListener implements SensorDataListener<T>{

        @Override
        public void SensorDataEvent(DataEvent<T> event) {

        }
    }

    class FileListener implements SensorDataListener<T>{

        @Override
        public void SensorDataEvent(DataEvent<T> event) {
            System.out.print(event.getSensorData().convertDatatoString());

        }
    }






}
