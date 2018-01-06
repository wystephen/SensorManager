package pers.steve.sensor.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jssc.SerialPortList;
import pers.steve.sensor.item.SensorJY901;
import pers.steve.sensor.item.SensorUWB;
import pers.steve.sensor.item.SerialAbstract;

import java.net.URL;
import java.util.ResourceBundle;

public class SensorUWBViewerController implements Initializable {


    @FXML
    public FlowPane mainPane;

    @FXML
    public HBox allBox;

    @FXML
    public VBox paraBox;

    @FXML
    public Button startButton;

    @FXML
    public Button stopButton;

    @FXML
    public Label nameLabel;


    @FXML
    public ChoiceBox<String> deviceChoice = null;

    protected ObservableList<String> deviceNameList =
            FXCollections.observableArrayList("Choice it to update");// device name list.

    protected String deviceNameString = ""; // device name , need be initialize.


    @FXML
    public ChoiceBox<Integer> speedChoice = null;

    protected ObservableList<Integer> speedList =
            FXCollections.observableArrayList(115200, 1192000, 460800); // NspeedList.

    protected int speedInt = 460800; // initial speed.


    @FXML
    ChoiceBox<String> nameChoice = null;
    protected ObservableList<String> nameList =
            FXCollections.observableArrayList("HEAD_UWB","LEFT_SHOULDER_UWB",
                    "RIGHT_SHOULDER_UWB");

    protected String nameOfUwB = "HEAD";


    public SensorUWB uwbBlack = new SensorUWB(); // IMU Sensor.

    public SerialAbstract serialInterface = new SerialAbstract();// serial reader.


    @FXML
    public TextField uwbDataText;
    StringProperty uwbDataString = new SimpleStringProperty();


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
//        mainPane.setMinWidth((paraBox.widthProperty().add(accChart.widthProperty().add(gyrChart.widthProperty().add(magChart.widthProperty())))).doubleValue());
        /**
         * Set up uwbDataText
         */
        uwbDataString.bindBidirectional(uwbDataText.textProperty());


        /**
         *  Set up ChoiceBOX
         */

        deviceChoice.itemsProperty().set(deviceNameList);

        deviceChoice.setOnMouseClicked(event -> {
//            System.out.println(event);
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
                System.out.println("DeviceName change to :" + deviceNameString);
            }
        });


        speedChoice.itemsProperty().set(speedList);


        speedChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedInt = newValue.intValue();
            }
        });


        nameChoice.itemsProperty().set(nameList);

        nameChoice.getSelectionModel().
                selectedItemProperty().
                addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                    nameOfUwB = newValue;
                                    Platform.runLater(() -> {
                                        nameLabel.setText(nameOfUwB);
                                    });

                                }
                            }

                );


        /**
         * Button
         */
        // Start sensor and GUI thread
        startButton.setOnAction(event -> {
            try {
                serialInterface = null;
                serialInterface = new SerialAbstract();
                if (deviceNameString.length() > 1 && speedInt > 9600) {
                    // device name and speed determined.
                    serialInterface.setSerialname(deviceNameString);
                    serialInterface.setNspeed(speedInt);

                    uwbBlack.setInterface(serialInterface);
                    uwbBlack.startSensor(0);
//                    imuJY.startFileOutput(0);
                    uwbBlack.startGUIOutput(0);

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


        // stop sensor and GUI thread
        stopButton.setOnAction(event -> {
            // Insure
            Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to stop sensor?\n" +
                            "Stop this process will cause a unusual\n situation of mouse!");
            a.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    uwbBlack.stopSensor(0);
//                    System.out.print("button type is: " + response.toString());
//                    imuJY.stopFileOutput(0);
                    uwbBlack.stopGUIOutput(0);

                } else {
//                    System.out.print("button type is: " + response.toString());
                }
            });
        });


    }
}

