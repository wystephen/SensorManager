package pers.steve.Sensors;

import java.util.EventObject;

public class SensorDataEvent extends EventObject {

    protected byte[] b_data = null;

    SensorDataEvent(){
        super(null);
        b_data = new byte[1];
    }


    SensorDataEvent(Object source, byte[] b_data){
        super(source);
        this.b_data = b_data;
    }

    public byte[] get_bytes(){
        return this.b_data;
    }

    public void setPara(Object source,byte[] b_data){
        super.source = source;
        this.b_data = b_data;

    }

}
