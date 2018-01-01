package pers.steve.Sensors;

import steve.edu.cn.SerialInterface;

public class SerialByteInterface extends SerialInterface<Byte[]> {
    public void SerialByteInterface()
    {
       super.SerialInterface();
    }

    /**
     *
     * @param serialname
     * @param nspeed
     * @param nbits
     * @param nevent
     * @param nstop
     */
    public void SerialByteInterface(
            String serialname,
            int nspeed,
            int nbits,
            String nevent,
            int nstop){
        super.SerialInterface(serialname,nspeed,nbits,nevent,nstop);
    }


    @Override
    public Byte[] ReadData() {
//        return super.ReadData();

    }
}
