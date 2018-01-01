package steve.edu.cn;

import gnu.io.*;
import pers.steve.Sensors.HardwareInteface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class SerialInterface<T> extends HardwareInteface<T> {
    private String serialname = "/dev/ttyUSB0";
    private int nspeed = 460800;
    private int nbits = 8;
    private String nevent = "n";
    private int nstop = 1;

    private CommPortIdentifier portIdentifier;
    private CommPort commPort;
    private SerialPort serialPort_local;


    /**
     * Construct function using default parameters
     */
    public void SerialInterface() {
        SerialInterface("/dev/ttyUSB0",
                460800,
                8, "n", 1);
    }

    /**
     * Initial SerialInterface using parameters.
     * An serial prot openned here.
     *
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
    ) {
        this.serialname = serialname;
        this.nspeed = nspeed;
        this.nbits = nbits;
        this.nevent = nevent;
        this.nstop = nstop;

        try {

            portIdentifier = CommPortIdentifier.getPortIdentifier(this.serialname);
            commPort = portIdentifier.open(this.serialname, 2000);
            serialPort_local = (SerialPort) commPort;

            //
            serialPort_local.setSerialPortParams(
                    nspeed,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );
            data_buffer = ByteBuffer.allocate(1024 * 10);

        } catch (NoSuchPortException e) {
            //TODO: Throw a new exception here.
            System.out.print("Cannot find the serial port :" + this.serialname);
            e.printStackTrace();
        } catch (PortInUseException e) {
            //TODO: Throw a new exception here.
            System.out.print("Port in use: " + this.serialname);
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            //TODO: Output all parameters here.
            System.out.print("UnsupportedCommOperatioin");
            e.printStackTrace();
        }


    }

    public class SerialEventListener implements SerialPortEventListener {

        public InputStream in = null;
        public byte[] bytes = null;

        public void serialEvent(SerialPortEvent serialPortEvent) {
            if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {

                    if (in == null) {

                        in = serialPort_local.getInputStream();
                    }
                    int bufflenth = in.available();

                    while (bufflenth > 0) {

                        bytes = new byte[bufflenth];
                        in.read(bytes);

                        data_buffer.put(bytes);
                        bufflenth = in.available();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ByteBuffer data_buffer;

    public T ReadData() {
        return null;
    }

    public T ReadData(int n) {
        return null;
    }


}



