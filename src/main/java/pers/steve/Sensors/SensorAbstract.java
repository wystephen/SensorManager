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
