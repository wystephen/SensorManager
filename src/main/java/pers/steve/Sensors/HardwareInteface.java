package pers.steve.Sensors;

import jssc.SerialPortException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public abstract class HardwareInteface<T extends SensorDataEvent> {
    //    protected SensorDataListener sensorDataListener = null;
    protected Collection listeners;

    /**
     *  Start interface. Default parameters will be adopted without set Parameter before.
     * @return
     */
    public abstract boolean StartInterface();

    public abstract boolean StopInterface() throws SerialPortException;

    public abstract boolean RestartInterface() throws SerialPortException;

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
     * create threads run listern.SensorDataEvent
     * @param event
     * @return
     */
    public boolean notifyListeners(T event) {
        if (null == listeners) {
            return false;
        }
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SensorDataListener listener = (SensorDataListener) iter.next();
            Runnable task = ()->{
                listener.SensorDataEvent(event);
            };
//            listener.SensorDataEvent(event);
            Thread t = new Thread(task);
            t.start();
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
