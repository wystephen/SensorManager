package pers.steve.Sensors;

import java.util.EventListener;

public interface SensorDataListener extends EventListener {
    public void SensorDataEvent(SensorOriginalDataEvent event);

}
