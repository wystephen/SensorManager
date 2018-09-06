package pers.steve.sensor.item;

import java.util.EventListener;

public interface SensorOriginalDataListener extends EventListener {
    public void SensorDataEvent(SensorOriginalDataEvent event) throws InterruptedException;

}
