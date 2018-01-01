package pers.steve.Sensors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public abstract class HardwareInteface {
    //    protected SensorDataListener sensorDataListener = null;
    protected Collection listeners;

    /**
     *  Start interface. Default parameters will be adopted without set Parameter before.
     * @return
     */
    public abstract boolean StartInterface();

    public abstract boolean StopInterface();

    public abstract boolean RestartInterface();

    /**
     * @param listener
     * @return
     */
    public boolean addListener(SensorDataListener listener) {
        if (null == listeners) {
            listeners = new HashSet();
        }
        listeners.add(listener);
        return true;
    }

    /**
     * reamove listener from hashset. Becareful!!!
     *
     * @param listener
     * @return
     */
    public boolean removeListener(SensorDataListener listener) {
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
     *
     * @param event
     * @return
     */
    public boolean notifyListeners(SensorDataEvent event) {
        if (null == listeners) {
            return false;
        }
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SensorDataListener listener = (SensorDataListener) iter.next();
            listener.SensorDataEvent(event);
        }
        return true;
    }

    /**
     * Clear all listeners.
     * @return
     */
    public boolean clearListeners(){
        if(null == listeners)
        {
            return false;
        }else{
            listeners.clear();
            return true;
        }
    }


}
