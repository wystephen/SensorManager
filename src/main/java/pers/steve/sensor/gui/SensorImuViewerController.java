package pers.steve.sensor.gui;

import javafx.beans.value.ObservableLongValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
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
    public ChoiceBox deviceChoice;

    @FXML
    public ChoiceBox speedChoice;

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

    protected ObservableLongValue speedValue;
    protected ObservableStringValue deviceNameValue;


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
//        SensorJY901 imuJY = new SensorJY901();
//        imuJY.setInterface()
        mainPane.setMinWidth(1600);


        deviceChoice.itemsProperty().set(deviceNameList);
        deviceChoice.valueProperty().set(deviceNameValue);

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


        speedChoice.itemsProperty().set(speedList);
        speedChoice.valueProperty().set(speedValue);


        Runnable tr = () -> {
            System.out.print(String.format("device:%s speed:%d", deviceNameValue.getValue(), speedValue.get()));
            try {
                Thread.sleep(400, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(tr);
        t.start();


    }


}
