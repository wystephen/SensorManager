package pers.steve.Sensors;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;

/**
 * @param <DataElementType>
 * @param <DataInterface>
 */
public abstract class SensorAbstract<DataElementType, DataInterface> {

    protected java.util.concurrent.SynchronousQueue<DataElementType> data_queue = new SynchronousQueue<DataElementType>();

    private Thread SensorDataAccepter;

    protected DataInterface data_interface_tool = null;

    public abstract boolean setInterface(DataInterface data_interface_tool);

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String sensorName) {
        SensorName = sensorName;
    }

    private String SensorName;

    protected HashSet<SensorDataListener<DataElementType>> listenerHashSet = null;


    protected boolean sensor_running_flag = false; // flag for sensor thread which read data from hardware.
    protected boolean fileout_running_flag = false; // flag for file output thread which write data to file.
    protected boolean gui_running_flag = false; // flag for gui thread which show the data online.


    public boolean isSensor_running_flag() {
        return sensor_running_flag;
    }

    public void setSensor_running_flag(boolean sensor_running_flag) {
        this.sensor_running_flag = sensor_running_flag;
    }

    public boolean isFileout_running_flag() {
        return fileout_running_flag;
    }

    public void setFileout_running_flag(boolean fileout_running_flag) {
        this.fileout_running_flag = fileout_running_flag;
    }

    public boolean isGui_running_flag() {
        return gui_running_flag;
    }

    public void setGui_running_flag(boolean gui_running_flag) {
        this.gui_running_flag = gui_running_flag;
    }

    protected boolean addDataListener(SensorDataListener<DataElementType> listener) {
        try {
            if (listenerHashSet == null) {
                listenerHashSet = new HashSet<SensorDataListener<DataElementType>>();
            }
            listenerHashSet.add(listener);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean removeDataListener(SensorDataListener<DataElementType> listener) {
        try {
            if (listenerHashSet != null) {
                listenerHashSet.remove(listener);
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public boolean notifyListeners(DataEvent<DataElementType> event) {
        try {
            if (null == listenerHashSet) {
                return false;
            }
            Iterator iter = listenerHashSet.iterator();
            while (iter.hasNext()) {
                SensorDataListener<DataElementType> listener =
                        (SensorDataListener<DataElementType>) iter.next();
                Runnable task = () -> {
                    listener.SensorDataEvent(event);
                };
                Thread t = new Thread(task);
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
