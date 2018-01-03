package pers.steve.sensor.item;

public abstract class SensorIMU<DataInterfere>
        extends SensorAbstract<IMUDataElement, DataInterfere>
        implements SensorInterface {

    private VisualListener guiListener;
    private FileListener fileListener;

    SensorIMU() {
        setSensorName("GeneralIMU");
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

    class VisualListener implements SensorDataListener<IMUDataElement> {

        @Override
        public void SensorDataEvent(DataEvent<IMUDataElement> event) {
            System.out.print("Show GUI HERE");
        }
    }

    class FileListener implements SensorDataListener<IMUDataElement> {

        @Override
        public void SensorDataEvent(DataEvent<IMUDataElement> event) {
//            System.out.print("Try to output file");
            System.out.print(event.getSensorData().convertDatatoString());

        }
    }


}
