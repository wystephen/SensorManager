package pers.steve.sensor.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jssc.SerialPortList;
import pers.steve.sensor.item.SensorJY901;
import pers.steve.sensor.item.SerialAbstract;

import java.net.URL;
import java.util.ResourceBundle;

public class SensorImuViewerController implements Initializable {
    @FXML
    public FlowPane mainPane;

    @FXML
    public Button startButton;

    @FXML
    public Button stopButton;

    @FXML
    public Label nameLabel;


    @FXML
    public ChoiceBox<String> deviceChoice;

    @FXML
    public ChoiceBox<Integer> speedChoice;

    @FXML
    public LineChart accChart;

    @FXML
    public LineChart gyrChart;

    @FXML
    public LineChart magChart;

//    protected FXCollections.observableArrayList speedList = new

    public SensorJY901 imuJY = new SensorJY901(); // IMU Sensor.

    public SerialAbstract serialInterface = new SerialAbstract();// serial reader.


    protected ObservableList<String> deviceNameList = FXCollections.observableArrayList("Choice it to update");
    protected ObservableList<Integer> speedList = FXCollections.observableArrayList(115200, 1192000, 460800);

    protected int speedInt = 460800;
    protected String deviceNameString = "";


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
        SensorJY901 imuJY = new SensorJY901();

//        imuJY.setInterface()
        mainPane.setMinWidth(1600);

        /**
         * ChoiceBOX
         */

        deviceChoice.itemsProperty().set(deviceNameList);
//        deviceChoice.valueProperty().set(deviceNameValue);

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
//        speedChoice.valueProperty().set(speedValue);

        speedChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedInt = newValue.intValue();
            }
        });


        /**
         * Button
         */
        /**
         * Start
         */
        startButton.setOnAction(event -> {
//            System.out.println(event.toString());
            try {
                serialInterface = new SerialAbstract();
//                } else {
//                    System.out.println("Select device name and speed first");
//                }
                if (deviceNameString.length() > 1 && speedInt > 9600) {
                    serialInterface.setSerialname(deviceNameString);
                    serialInterface.setNspeed(speedInt);

                    imuJY.setInterface(serialInterface);
                    imuJY.startSensor(0);
                    imuJY.startFileOutput(0);

                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("carefully select serial port parameters");
                    a.setHeaderText("Error in select port parameters");
                    a.setContentText(String.format("device:%s speed:%d \n are unacceptable!",
                            deviceNameString, speedInt));
                    a.show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        stopButton.setOnAction(event -> {
            imuJY.stopSensor(0);
            imuJY.stopFileOutput(0);
        });


    }


}

