package pers.steve.Sensors;

import java.util.EventListener;

public interface SensorDataListener<T> extends EventListener{
    public void SensorDataEvent(DataEvent<T> event);
}
