package pers.steve.sensor.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jssc.SerialPortList;
import pers.steve.sensor.item.*;

import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SensorImuViewerController implements Initializable {
    @FXML
    public FlowPane mainPane;

    @FXML
    public HBox allBox;

    @FXML
    public Button startButton;

    @FXML
    public Button stopButton;

    @FXML
    public Label nameLabel;


    @FXML
    public ChoiceBox<String> deviceChoice = null;

    @FXML
    public ChoiceBox<Integer> speedChoice = null;

    @FXML
    public ScatterChart<Number, Number> accChart;

    final private NumberAxis acc_time_axis = new NumberAxis();//;(0, 100.0, 2);
    final private NumberAxis acc_value_axis = new NumberAxis(-20, 20, 1.0);


    private XYChart.Series<Number, Number> serial_acc_x = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> serial_acc_y = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> serial_acc_z = new XYChart.Series<Number, Number>();

    private ObservableList<XYChart.Data<Number, Number>> accXList =
            FXCollections.observableArrayList();
    //            FXCollections.observableArrayList(new XYChart.Data<>(0.0, 0.0));
    private ObservableList<XYChart.Data<Number, Number>> accYList =
            FXCollections.observableArrayList();
    //        FXCollections.observableArrayList(new XYChart.Data<>(0.0, 0.0));
    private ObservableList<XYChart.Data<Number, Number>> accZList =
            FXCollections.observableArrayList();
//            FXCollections.observableArrayList(new XYChart.Data<>(0.0, 0.0));

//    private ConcurrentLinkedDeque<Number> data_acc_x

    @FXML
    public LineChart<Number, Number> gyrChart = null;

    @FXML
    public LineChart<Number, Number> magChart = null;

//    protected FXCollections.observableArrayList speedList = new

    public SensorJY901 imuJY = new SensorJY901(); // IMU Sensor.

    public SerialAbstract serialInterface = new SerialAbstract();// serial reader.


    protected ObservableList<String> deviceNameList = FXCollections.observableArrayList("Choice it to update");
    protected ObservableList<Integer> speedList = FXCollections.observableArrayList(115200, 1192000, 460800);

    protected int speedInt = 460800;
    protected String deviceNameString = "";

    private ExecutorService executor;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * Set up Chart
         */
        serial_acc_x.setName("Acc_X");
        serial_acc_y.setName("Acc_Y");
        serial_acc_z.setName("Acc_Z");

        serial_acc_x.dataProperty().set(accXList);
        serial_acc_y.dataProperty().set(accYList);
        serial_acc_z.dataProperty().set(accZList);


        acc_time_axis.setLabel("Time / s");
        acc_value_axis.setLabel("acc /(m/s/s)");
        acc_time_axis.setAutoRanging(false);
        acc_value_axis.setAutoRanging(false);


        accChart.getData().addAll(serial_acc_x, serial_acc_y, serial_acc_z);
//        accChart.setAnimated(false);
        accChart.setTitle("Acc");


        SensorJY901 imuJY = new SensorJY901();

        imuJY.setGUIEventListener(new SensorDataListener<IMUDataElement>() {

            int dataMaxLength = 2000;

            @Override
            public void SensorDataEvent(DataEvent<IMUDataElement> event) {
//                System.out.println(event);
                IMUDataElement sensorData = event.getSensorData();
                double acc_time = new Double(sensorData.getTime_stamp()) + 0.00001;
                double acc_x = new Double(sensorData.getAcc()[0]) + 0.00001;
                double acc_y = new Double(sensorData.getAcc()[1]) + 0.00001;
                double acc_z = new Double(sensorData.getAcc()[2]) + 0.00001;


                double small_num = 0.00000000001;
                Platform.runLater(() -> {
//                    double first_time = accXList.get(0).getXValue().doubleValue();
                    accXList.add(new XYChart.Data<Number, Number>(acc_time, acc_x));
                    accYList.add(new XYChart.Data<Number, Number>(acc_time, acc_y));
                    accZList.add(new XYChart.Data<Number, Number>(acc_time, acc_z));


                    if (accXList.size() > 5000) {
                        accXList.remove(0,1000);
                        accYList.remove(0,1000);
                        accZList.remove(0,1000);
                    }


                });
//
//
            }
        });


        mainPane.setMinWidth(1600);

        /**
         * ChoiceBOX
         */

        deviceChoice.itemsProperty().set(deviceNameList);

        deviceChoice.setOnMouseClicked(event -> {
            System.out.println(event);
            SerialPortList portList = new SerialPortList();
            String[] name_list = portList.getPortNames();
            deviceNameList.clear();
            for (String s : name_list) {
                System.out.println(s);
                deviceNameList.add(s);
            }
            portList = null;
        });

        deviceChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                deviceNameString = newValue;
            }
        });


        speedChoice.itemsProperty().set(speedList);

        speedChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedInt = newValue.intValue();
            }
        });


        /**
         * Button
         */
        startButton.setOnAction(event -> {
            try {
                serialInterface = new SerialAbstract();
                if (deviceNameString.length() > 1 && speedInt > 9600) {
                    // device name and speed determined.
                    serialInterface.setSerialname(deviceNameString);
                    serialInterface.setNspeed(speedInt);

                    imuJY.setInterface(serialInterface);
                    imuJY.startSensor(0);
//                    imuJY.startFileOutput(0);
                    accXList.clear();
                    accYList.clear();
                    accZList.clear();
                    imuJY.startGUIOutput(0);

                } else {
                    // device name and speed error.
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("carefully select serial port parameters");
                    a.setHeaderText("Error in select port parameters");
                    a.setContentText(String.format("device:%s \nspeed:%d \n are unacceptable!",
                            deviceNameString, speedInt));
                    a.show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        stopButton.setOnAction(event -> {
            // Insure
            Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to stop sensor?\n" +
                            "Stop this process will cause a unusual\n situation of mouse!");
            a.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    imuJY.stopSensor(0);
//                    System.out.print("button type is: " + response.toString());
//                    imuJY.stopFileOutput(0);
                    imuJY.stopGUIOutput(0);

                } else {
//                    System.out.print("button type is: " + response.toString());
                }
            });
        });


    }

    @FXML
    public void testClicked(Event event) {
        System.out.println(event.toString());
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
//                addDataToSeries();
            }
        }.start();
    }

}

