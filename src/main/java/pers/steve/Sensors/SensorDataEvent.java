package pers.steve.Sensors;

import java.util.EventObject;

public class SensorDataEvent extends EventObject {

    protected byte[] b_data = null;


    SensorDataEvent(Object source, byte[] b_data){
        super(source);
        this.b_data = b_data;
    }

    public byte[] get_bytes(){
        return this.b_data;
    }

}
