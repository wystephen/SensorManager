package pers.steve.Sensors;

public abstract class SensorIMU<DataInterfere>
        extends SensorAbstract<IMUDataElement, DataInterfere>
        implements ISensor {

    private VisualListener gui_listener;
    private FileListener fileListener;

    public SensorIMU() {
        setSensorName("GeneralIMU");
    }


    @Override
    public boolean StartGUIOutput(int state) {
        if (null != gui_listener) {
            System.out.print("You should shop GUI Output first.");
            return false;
        }
        gui_listener = new VisualListener();
        if (addDataListener(gui_listener)) {

            return true;
        } else {

            return false;
        }
    }


    @Override
    public boolean StopGUIOutput(int state) {
        if (removeDataListener(gui_listener)) {
            gui_listener = null;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean StartFileOutput(int state) {
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

    class VisualListener implements SensorDataListener<IMUDataElement> {

        @Override
        public void SensorDataEvent(DataEvent<IMUDataElement> event) {
            System.out.print("Show GUI HERE");
        }
    }

    class FileListener implements SensorDataListener<IMUDataElement> {

        @Override
        public void SensorDataEvent(DataEvent<IMUDataElement> event) {
            System.out.print("Try to output file");
        }
    }


}
