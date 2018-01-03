package pers.steve.sensor.item;

import jssc.SerialPortException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public abstract class HardwareInteface {
    private Collection listeners; // Collection for all listener. (HashSet in default)
//    protected String TName = "SensorOriginalDataEvent";

    /**
     * Start interface. Default parameters will be adopted without set Parameter before.
     *
     * @return
     */
    public abstract boolean StartInterface();

    /**
     * Stop hardware and clear the listeners.
     *
     * @return
     * @throws SerialPortException
     */
    public abstract boolean StopInterface();

    /**
     * @return
     * @throws SerialPortException
     */
    public abstract boolean RestartInterface();

    /**
     * Add a Listener for the SensorDataArrive Event.
     *
     * @param listener
     * @return
     */
    public boolean addListener(SensorOriginalDataListener listener) {
        if (null == listeners) {
            listeners = new HashSet();
        }
        listeners.add(listener);
        return true;
    }

    /**
     * Remove listener from HashSet. Be careful!!!
     *
     * @param listener
     * @return
     */
    public boolean removeListener(SensorOriginalDataListener listener) {
        try {
            if (listeners != null) {

                listeners.remove(listener);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Create threads run listener.SensorOriginalDataEvent in listeners.
     *
     * @param event
     * @return
     */
    public boolean notifyListeners(SensorOriginalDataEvent event) {
        if (null == listeners) {
            return false;
        }
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SensorOriginalDataListener listener = (SensorOriginalDataListener) iter.next();
            Runnable task = () -> {
                try {

                    listener.SensorDataEvent(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
//            listener.SensorOriginalDataEvent(event);
            Thread t = new Thread(task);
            t.start();
        }
        return true;
    }

    /**
     * Clear all listeners.
     *
     * @return
     */
    public boolean clearListeners() {
        if (null == listeners) {
            return false;
        } else {
            listeners.clear();
            return true;
        }
    }


}
