package pers.steve.Sensors;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        return false;
    }

    public class SerialListener implements SensorOriginalDataListener {
        protected SynchronousQueue<Byte> byte_queue = new SynchronousQueue<>();

        protected IMUDataElement imu_data=null;
//        protected SimpleDateFormat df = new SimpleDateFormat();



        protected Runnable data_process = () -> {
            byte[] buf = new byte[11];
            try {
                int end_num = 11;
                for (int i = 0; i < end_num; ++i) {
                    buf[i] = byte_queue.take().byteValue();
                    if(i==0 && buf[0] != 0x55 ){
                        System.out.print("throw data");
                        return;
                    }

                }
                switch (buf[1]){
                    case 0x50:
                        /// Time
                        if(imu_data!=null){
                            DataEvent<IMUDataElement> event = new DataEvent<IMUDataElement>(this);
                            event.setSensorData(imu_data);
                            notifyListeners(event);
                        }
                        imu_data = new IMUDataElement();
                        int year = (int) (buf[2] & 0xFF);
                        int mon = (int) (buf[3] &0xFF);
                        int day = (int) (buf[4] & 0xFF);
                        int hour = (int) (buf[5] & 0xFF);
                        int min = (int) (buf[6] & 0xFF);
                        int sec = (int) (buf[7] & 0xFF);
                        int ms = (int) ((buf[8] & 0xFF)|((buf[9] & 0xFF)<<8));
                        StringBuilder sb = new StringBuilder();
                        sb.append("20");
                        sb.append(year);
                        sb.append("-");
                        sb.append(mon);
                        sb.append("-");
                        sb.append(day);
                        sb.append("-");
                        sb.append(hour);
                        sb.append("-");
                        sb.append(min);
                        sb.append("-");
                        sb.append(sec);
                        ZonedDateTime zdt = ZonedDateTime.parse(
                                sb.toString(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
                        );
                        Timestamp ts = Timestamp.from(zdt.toInstant());
                        long time_int = ts.getTime();

                        imu_data.setTime_stamp((double)(time_int)+((double)(ms))/1000.0);

                        break;

                    case 0x51:

                        break;

                    case 0x52:

                        break;

                    case 0x53:

                        break;

                    case 0x54:

                        break;

                    case 0x55:

                        break;
                    case 0x56:

                        break;

                    default:
                        System.out.print("Some Error may happend in the JY901 class.");
                        break;

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        };

        @Override
        public void SensorDataEvent(SensorOriginalDataEvent event) throws InterruptedException {
            byte[] bytes = event.get_bytes();
            for (byte tb : bytes) {
                byte_queue.put(new Byte(tb));
            }
            if (byte_queue.size() >= 11) {
                new Thread(data_process).start();
            }


        }
    }


}
