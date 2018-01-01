package steve.edu.cn;

import pers.steve.Sensors.HardwareInteface;

public class SerialInterface extends HardwareInteface {
    private String serialname = "/dev/ttyUSB0";
    private int nspeed = 460800;
    private int nbits = 8;
    private String nevent = "n";
    private int nstop = 1;

    /**
     * Construct function using default parameters
     */
    public void SerialInterface(){
        String serialname = "/dev/ttyUSB0";
        int nspeed = 460800;
        int nbits = 8;
        String nevent = "n";
        int nstop = 1;
    }

    /**
     * Initial SerialInterface using parameters.
     * @param serialname
     * @param nspeed
     * @param nbits
     * @param nevent
     * @param nstop
     */
    public void SerialInterface(
            String serialname,
            int nspeed,
            int nbits,
            String nevent,
            int nstop
    ){
        this.serialname = serialname;
        this.nspeed = nspeed;
        this.nbits = nbits;
        this.nevent = nevent;
        this.nstop = nstop;
    }

    public Object ReadData() {
        return null;
    }


}



