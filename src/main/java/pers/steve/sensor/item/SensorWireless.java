package pers.steve.sensor.item;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * For Wireless sensors like wifi,ble, uwb.
 *
 * @param <T>
 * @param <DataInterface>
 */
public abstract class SensorWireless<T extends WirelessDataElement, DataInterface>
        extends SensorAbstract<T, DataInterface>
        implements SensorInterface {

    private SensorDataListener<T> guiListener;
    private FileListener fileListener;


    SensorWireless() {
        setSensorName("GeneralWireless");
    }


    @Override
    public boolean setGUIEventListener(SensorDataListener<? extends SensorDataElement> listener) {
        guiListener = (SensorDataListener<T>) listener;
        return true;
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
            try {

                fileListener.fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileoutRunningFlag = false;
            fileListener = null;
            return true;
        } else {
            return false;
        }
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
                guiListener = null;
                guiRunningFlag = false;
                return true;
            } else {
                return false;
            }

        } else {
            return false;

        }
    }

    class VisualListener implements SensorDataListener<T> {

        @Override
        public void SensorDataEvent(DataEvent<T> event) {

        }
    }

    class FileListener implements SensorDataListener<T> {

        public FileWriter fileWriter;

        FileListener() {

            try {

                fileWriter = new FileWriter(dataSaveFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int writedCounter = 0;

        @Override
        public void SensorDataEvent(DataEvent<T> event) {
            try {


                synchronized (this) {
                    fileWriter.write(event.sensorData.convertDatatoString());
                    writedCounter++;
                    if (writedCounter > 3) {

                        fileWriter.flush();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
