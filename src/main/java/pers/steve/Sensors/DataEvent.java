package pers.steve.Sensors;

import javafx.event.Event;
import javafx.event.EventType;

import java.util.EventObject;

public class DataEvent<T> extends EventObject {


    public T sensorData;


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DataEvent(Object source) {
        super(source);
    }

    public T getSensorData() {
        return sensorData;
    }


    public void setSensorData(T sensorData) {
        this.sensorData = sensorData;
    }
}
