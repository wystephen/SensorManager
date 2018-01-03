package pers.steve.sensor.item;

import java.util.EventListener;

public interface SensorDataListener<T> extends EventListener{
    public void SensorDataEvent(DataEvent<T> event);
}
