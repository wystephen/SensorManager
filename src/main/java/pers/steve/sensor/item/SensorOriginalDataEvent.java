package pers.steve.sensor.item;

import java.util.EventObject;

public class SensorOriginalDataEvent extends EventObject {

    protected byte[] b_data = null;

    SensorOriginalDataEvent(){
        super(new String());
        b_data = new byte[1];
    }


    SensorOriginalDataEvent(Object source, byte[] b_data){
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
