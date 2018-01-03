package pers.steve.sensor.item;

import java.util.EventListener;

/**
 * SensorDataListener
 * @param <T> DataElement type.
 */
public interface SensorDataListener<T> extends EventListener{
    public void SensorDataEvent(DataEvent<T> event);
}
