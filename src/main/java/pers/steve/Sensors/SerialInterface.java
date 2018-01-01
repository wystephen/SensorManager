package pers.steve.Sensors;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

public class SerialInterface extends HardwareInteface {

    private String serialname = "/dev/ttyUSB0";
    private int nspeed = 460800;
    private int nbits = 8;
    private String nevent = "n";

    public String getSerialname() {
        return serialname;
    }

    public void setSerialname(String serialname) {
        this.serialname = serialname;
    }

    public int getNspeed() {
        return nspeed;
    }

    public void setNspeed(int nspeed) {
        this.nspeed = nspeed;
    }

    public int getNbits() {
        return nbits;
    }

    public void setNbits(int nbits) {
        this.nbits = nbits;
    }

    public String getNevent() {
        return nevent;
    }

    public void setNevent(String nevent) {
        this.nevent = nevent;
    }

    public int getNstop() {
        return nstop;
    }

    public void setNstop(int nstop) {
        this.nstop = nstop;
    }

    private int nstop = 1;

    private SerialPort serialPort_local = null;


    /**
     * Set and start a serial port parameters.
     * An serial prot openned here.
     *
     * @param serialname
     * @param nspeed
     * @param nbits
     * @param nevent
     * @param nstop
     */
    public boolean startSerialPara(
            String serialname,
            int nspeed,
            int nbits,
            String nevent,
            int nstop
    ) {

        try {

            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialname);
            CommPort commPort = portIdentifier.open(serialname, 2000);
            this.serialPort_local = (SerialPort) commPort;

            //
            this.serialPort_local.setSerialPortParams(
                    nspeed,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );
            this.serialPort_local.addEventListener(new SerialEventListener());


        } catch (NoSuchPortException e) {
            //TODO: Throw a new exception here.
            System.out.print("Cannot find the serial port :" + serialname);
            e.printStackTrace();
            return false;
        } catch (PortInUseException e) {
            //TODO: Throw a new exception here.
            System.out.print("Port in use: " + serialname);
            e.printStackTrace();
            return false;
        } catch (UnsupportedCommOperationException e) {
            //TODO: Output all parameters here.
            System.out.print("UnsupportedCommOperatioin");
            e.printStackTrace();
            return false;
        } catch (TooManyListenersException e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }

    @Override
    public boolean StartInterface() {
        if (startSerialPara(serialname, nspeed, nbits, nevent, nstop)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean StopInterface() {
        serialPort_local.close();
        super.clearListeners();
        return false;
    }

    @Override
    public boolean RestartInterface() {
        serialPort_local.close();
        serialPort_local = null;
        startSerialPara(serialname, nspeed, nbits, nevent, nstop);
        return false;
    }


    public class SerialEventListener implements SerialPortEventListener {

        public InputStream in = null;
        public byte[] bytes = null;

        public void serialEvent(SerialPortEvent serialPortEvent) {
            if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {

                    if (null == in) {
                        // initial input stream once.
                        in = serialPort_local.getInputStream();
                    }

                    int buflength = in.available();

                    while (buflength > 0) {

                        bytes = new byte[buflength];
                        in.read(bytes);
//                        SensorDataEvent event = new SensorDataEvent(this,bytes);
                        notifyListeners(new SensorDataEvent(this, bytes));


                        buflength = in.available();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}





