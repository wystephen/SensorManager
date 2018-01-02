package pers.steve.Sensors;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SensorJY901 extends SensorIMU<SerialInterface> {


    /**
     * Start sensor means set up a new thread for reading
     *
     * @param State
     * @return
     */
    @Override
    public boolean StartSensor(int State) {
        if (null == data_interface_tool) {
            System.out.print("data interface tool is null");
            return false;
        }
        data_interface_tool.addListener(new SerialListener());
        data_interface_tool.StartInterface();


        return true;
    }

    /**
     * @param State
     * @return
     */
    @Override
    public boolean StopSensor(int State) {
        return false;
    }


    @Override
    public boolean setInterface(SerialInterface data_interface_tool) {
        this.data_interface_tool = data_interface_tool;
        return true;
    }

    public class SerialListener implements SensorOriginalDataListener {
        protected ArrayBlockingQueue<Byte> byte_queue = new ArrayBlockingQueue<>(1000);

        protected IMUDataElement imu_data = null;
//        protected SimpleDateFormat df = new SimpleDateFormat();

        public SerialListener() {
            super();
            Thread t = new Thread(data_process);
            t.start();

        }




        /**
         * Process buf from serial port.
         */
        protected Runnable data_process = () -> {
            while (true) {


//                System.out.print("start data process");
                byte[] buf = new byte[11];
                try {
                    int end_num = 11;
                    for (int i = 0; i < end_num; ++i) {
                        buf[i] = byte_queue.take();
                        if (i == 0 && buf[0] != 0x55) {
                            System.out.print("throw data");
                            throw new Exception("Throw data");
                        }

                    }
//                    System.out.print("after copy data");
                    switch (buf[1]) {
                        case 0x50:
                            /// Time
                            if (imu_data != null) {
                                DataEvent<IMUDataElement> event = new DataEvent<IMUDataElement>(this);
                                event.setSensorData(imu_data);
                                notifyListeners(event);
                            }
                            imu_data = new IMUDataElement();
                            int year = 0;
                            year = (int) (buf[2] & 0xFF);
                            int mon = 0;
                            mon = (int) (buf[3] & 0xFF);
                            int day = 0;
                            day = (int) (buf[4] & 0xFF);
                            int hour = 0;
                            hour = (int) (buf[5] & 0xFF);
                            int min = 0;
                            min = (int) (buf[6] & 0xFF);
                            int sec = 0;
                            sec = (int) (buf[7] & 0xFF);
                            int ms = 0;
                            ms = (int) (((buf[8] & 0xFF) | ((buf[9] & 0xFF) << 8)));
                            Timestamp ts = Timestamp.valueOf(String.format("20%02d-%02d-%02d %02d:%02d:%02d", year, mon, day, hour, min, sec));
                            long time_int = ts.getTime();
                            imu_data.setTime_stamp((double) time_int + ((double) (ms)) / 1000.0);

                            break;

                        case 0x51:
                            /// Acc and temperature
                            int ax_int = 0;
                            int ay_int = 0;
                            int az_int = 0;
                            int temperature_int = 0;
                            ax_int = (int) ((buf[2] & 0xFF) | ((buf[3] & 0xFF) << 8));
                            ay_int = (int) ((buf[4] & 0xFF) | ((buf[5] & 0xFF) << 8));
                            az_int = (int) ((buf[6] & 0xFF) | ((buf[7] & 0xFF) << 8));
                            temperature_int = (int) ((buf[8] & 0xFF) | ((buf[9] & 0xFF) << 8));

                            double[] acc_tmp = new double[3];
                            double[] t = new double[1];
                            acc_tmp[0] = ((double) ax_int)/32768.0*16.0;
                            acc_tmp[1] = ((double) ay_int)/32768.0*16.0;
                            acc_tmp[2] = ((double) az_int)/32768.0*16.0;
                            imu_data.setAcc(acc_tmp);
                            t[0] = ((double) temperature_int) /100.0;
                            imu_data.setTemp(t);

                            break;

                        case 0x52:
                            ///gyr
                            int gx_int = 0;
                            int gy_int = 0;
                            int gz_int = 0;
                            gx_int = (int) ((buf[2] & 0xFF) | ((buf[3] & 0xFF) << 8));
                            gy_int = (int) ((buf[4] & 0xFF) | ((buf[5] & 0xFF) << 8));
                            gz_int = (int) ((buf[6] & 0xFF) | ((buf[7] & 0xFF) << 8));

                            double tmp_gyr[] = new double[3];
                            tmp_gyr[0] = ((double)gx_int)/32768.0*2000.0;
                            tmp_gyr[1] = ((double)gy_int)/32768.0*2000.0;
                            tmp_gyr[2] = ((double)gz_int)/32768.0*2000.0;
                            imu_data.setGyr(tmp_gyr);

                            break;

                        case 0x53:

                            break;

                        case 0x54:

                            break;

                        case 0x55:

                            break;
                        case 0x56:

                            break;
                    }
//                    System.out.print("after process");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }


        };

        @Override
        public void SensorDataEvent(SensorOriginalDataEvent event) throws InterruptedException {
//            System.out.print(new String(event.b_data));
            byte[] bytes = event.get_bytes();
            for (byte tb : bytes) {
                byte_queue.put(new Byte(tb));
            }
            System.out.println("byte queue size: " + byte_queue.size());


        }
    }


}
