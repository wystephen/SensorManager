package pers.steve.sensor.item;

import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *  An abstract class of HardwareAbstract like( serial port, tcp , bluetooth...).
 *  Provide the basic event notify interface.
 */
public abstract class HardwareAbstract {
    private Collection listenerCollection; // Collection for all listener. (HashSet in default)

    /**
     * Start interface. Default parameters will be adopted without set Parameter before.
     *
     * @return
     */
    public abstract boolean StartInterface();

    /**
     * Stop hardware and clear the listenerCollection.
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
        if (null == listenerCollection) {
            listenerCollection = new HashSet();
        }
        listenerCollection.add(listener);
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
            if (listenerCollection != null) {

                listenerCollection.remove(listener);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Create threads run listener.SensorOriginalDataEvent in listenerCollection.
     *
     * @param event
     * @return
     */
    public boolean notifyListeners(SensorOriginalDataEvent event) {
        if (null == listenerCollection) {
            return false;
        }
        synchronized (this){
            Iterator iter = listenerCollection.iterator();
            ArrayList<Thread> thread_list = new ArrayList<Thread>();
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
                try{
                 Thread t = new Thread(task);
                t.start();
//                t.join();
                    thread_list.add(t);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


            for(Thread t:thread_list){
                try{

                    t.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    /**
     * Clear all listenerCollection.
     *
     * @return
     */
    public boolean clearListeners() {
        if (null == listenerCollection) {
            return false;
        } else {
            listenerCollection.clear();
            return true;
        }
    }


}
