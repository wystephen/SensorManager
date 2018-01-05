package pers.steve.sensor.item;

/**
 * for IMU.
 *
 * @param <DataInterfere>
 */
public abstract class SensorIMU<DataInterfere>
        extends SensorAbstract<IMUDataElement, DataInterfere>
        implements SensorInterface {

    private SensorDataListener<IMUDataElement> guiListener;
    private FileListener fileListener;

    SensorIMU() {
        setSensorName("GeneralIMU");
    }

    @Override
    public boolean setGUIEventListener(SensorDataListener<? extends SensorDataElement> listener) {
        guiListener = (SensorDataListener<IMUDataElement>) listener;
        return true;
    }

    @Override
    public boolean startGUIOutput(int state) {
            if (addDataListener(guiListener)) {

                return true;
            } else {

                return false;
            }

    }


    @Override
    public boolean stopGUIOutput(int state) {
        if (guiRunningFlag == true) {
            if (removeDataListener(guiListener)) {
//                guiListener = null;
                guiRunningFlag = false;
                return true;
            } else {
                return false;
            }

        } else {
            return false;

        }


    }


    @Override
    public boolean startFileOutput(int state) {
        if (false != fileoutRunningFlag) {
            System.out.print("You should stop File Output First");
            return false;
        } else {
            fileListener = new FileListener();

            if (addDataListener(fileListener)) {
                fileoutRunningFlag = true;
                return true;
            } else {
                return false;
            }
        }

    }


    @Override
    public boolean stopFileOutput(int state) {
        if (true == fileoutRunningFlag) {
            removeDataListener(fileListener);
            fileoutRunningFlag = false;
            fileListener = null;
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
//            System.out.print("Try to output file");
            System.out.print("sensor file out runing:" + event.getSensorData().convertDatatoString());

        }
    }


}
