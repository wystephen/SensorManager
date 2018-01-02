package pers.steve.Sensors;

import java.util.EventListener;

public interface SensorOriginalDataListener extends EventListener {
    public void SensorDataEvent(SensorOriginalDataEvent event);

}
