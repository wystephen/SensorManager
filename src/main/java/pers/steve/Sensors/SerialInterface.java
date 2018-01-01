package pers.steve.Sensors;

import gnu.io.*;
import pers.steve.Sensors.HardwareInteface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.TooManyListenersException;

public class SerialInterface<T> extends HardwareInteface<T> {
    private String serialname = "/dev/ttyUSB0";
    private int nspeed = 460800;
    private int nbits = 8;
    private String nevent = "n";
    private int nstop = 1;

    private SerialPort serialPort_local  = null;


    /**
     * Construct function using default parameters
     */
    public void SetSerialPara() {

    }

    @Override
    public boolean StartInterface() {

        return false;
    }

    @Override
    public boolean StopInterface() {
        return false;
    }

    @Override
    public boolean RestartInterface() {
        return false;
    }








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
     public void StartSerialPara(
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
        } catch (PortInUseException e) {
            //TODO: Throw a new exception here.
            System.out.print("Port in use: " + serialname);
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            //TODO: Output all parameters here.
            System.out.print("UnsupportedCommOperatioin");
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }


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


                        buflength = in.available();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


}



