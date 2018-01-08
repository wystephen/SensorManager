package pers.steve.sensor.item;

import java.util.concurrent.ArrayBlockingQueue;

public class SensorUWB extends SensorWireless<UWBDataElement, SerialAbstract> {
    @Override
    public boolean setInterface(SerialAbstract data_interface_tool) {
        this.dataInterfaceTool = data_interface_tool;
        return false;
    }


    /**
     * Start sensor means set up a new thread for reading
     * TODO: Try to avoid duplicated code of this funciotn.
     *
     * @param State
     * @return
     */
    @Override
    public boolean startSensor(int State) {
        if (null == dataInterfaceTool) {
            System.out.print("data interface tool is null");
            return false;
        }
        if (sensorRunningFlag == true) {
            System.out.print("Sensor is running.");
            return false;
        }
        sensorRunningFlag = true;
        dataInterfaceTool.addListener(new SerialListenerUWB());
        dataInterfaceTool.StartInterface();

        return true;
    }

    /**
     * @param State
     * @return
     */
    @Override
    public boolean stopSensor(int State) {
        if (sensorRunningFlag) {
            dataInterfaceTool.StopInterface();
            sensorRunningFlag = false;
            return true;
        } else {
            System.out.print("Sensor is not running now.");
            return true;
        }

    }


    /**
     * Listener for UWB use DAPS embedded system.
     */
    class SerialListenerUWB implements SensorOriginalDataListener {

        protected ArrayBlockingQueue<String> strQueue = new ArrayBlockingQueue<>(100);
        protected Runnable dataProcess = null;


        SerialListenerUWB() {
            dataProcess = () -> {
                UWBDataElement uwbDataElement = null;

                String localString = null;

                // in rang result,
                // 1:the first result of this measurements circle,
                // 0:others.
                String A = ""; // Key word.
                String N = ""; // MAC address
                String X = ""; // distance , %4.4f, float.
                String Y = "";// LQI , 0-100, int.

                String T = "";// time since device turn on.

                while (true) {
                    try {

                        localString = strQueue.take();
                        char keyWord = ' ';
                        keyWord = localString.charAt(localString.indexOf('@') + 1);
//                        System.out.print("keyword: " + keyWord);
                        switch (keyWord) {
                            case 'W':
                                // Error or Warning from device.
                                System.out.print("Some error happend:" + localString);
                                break;

                            case 'B':
                                // broadcast information from other devices.
                                System.out.print("Information:" + localString);
                                break;

                            case 'E':
                                // Error
                                System.out.print("ERROR:" + localString);
                                break;

                            case 'L':
                                // position of tag
                                System.out.print("Position:" + localString);
                                break;

                            case 'R':
                                // range information
//                                System.out.print("Range:" + localString);
                                String[] tmp_str_array = localString.split(" ");
                                T = tmp_str_array[0].substring(tmp_str_array[0].indexOf('[') + 1, tmp_str_array[0].indexOf(']'));
                                A = tmp_str_array[3];
                                N = tmp_str_array[4];
                                X = tmp_str_array[5];
                                Y = tmp_str_array[6];
                                if (A.equals("F1")) {
                                    // begin distance measurement circle.
                                    if (uwbDataElement != null) {
                                        DataEvent<UWBDataElement> event = new DataEvent<>(this);
                                        event.setSensorData(uwbDataElement);
                                        notifyListeners(event);
                                    }
                                    uwbDataElement = new UWBDataElement();
                                    uwbDataElement.setSystem_time_stamp(((double) System.currentTimeMillis()) / 1000.0);
                                    uwbDataElement.setTime_stamp(Double.valueOf(T));

                                } else {
                                    // distance  measurements.
                                    double[] ds = new double[2];
                                    ds[0] = Double.valueOf(X);
                                    ds[1] = Double.valueOf(Y);
                                    if (uwbDataElement != null) {
                                        // To avoid null pointer error.

                                        uwbDataElement.addMeasurement(N, ds);
                                    }

                                }


                                break;
                            default:
                                throw new Exception("Unrecognizable sensor data: " + localString);

                        }
                        if (strQueue.size() < 1) {
                            Thread.sleep(0, 1000);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

            };

            Thread t = new Thread(dataProcess);
            t.start();

        }


        @Override
        public void SensorDataEvent(SensorOriginalDataEvent event) {
//            System.out.print(new String(event.get_bytes()));
//            System.out.print("---------------------------------------");
            try {

                strQueue.put(new String(event.get_bytes()));
//                System.out.println("strQueue size is " + strQueue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
