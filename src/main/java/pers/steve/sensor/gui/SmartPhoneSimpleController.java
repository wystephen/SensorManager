package pers.steve.sensor.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import pers.steve.sensor.item.DataEvent;
import pers.steve.sensor.item.IMUDataElement;
import pers.steve.sensor.item.SensorDataListener;
import pers.steve.sensor.item.WirelessDataElement;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

public class SmartPhoneSimpleController extends SensorWriteFileInterface
        implements Initializable {

    @FXML
    public FlowPane mainPane;

    @FXML
    public Label nameLabel;

    @FXML
    public ChoiceBox nameChoice;

    protected ObservableList<String> nameList =
            FXCollections.observableArrayList("HAND_SMARTPHONE", "Default");


    String nameString = "Default";

    @FXML
    public ChoiceBox portChoice;

    protected ObservableList<Integer> portList =
            FXCollections.observableArrayList(6666, 6667, 6668, 6669, 6670);
    int port = 6666;


    @FXML
    public Button startButton;

    @FXML
    public Button stopButton;

    @FXML
    ListView<String> bleDataList;
    ObservableList<String> bleDataObservableList;

    HashSet<SensorDataListener<IMUDataElement>> imuListeners = new HashSet<>();
    HashSet<SensorDataListener<WirelessDataElement>> bleListeners = new HashSet<>();

    Thread socketServerThread = null;
    ServerSocket serverSocket = null;
    boolean serverRunningFlag = false;


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

        bleDataObservableList = FXCollections.observableArrayList("Empty");
        bleDataList.setItems(bleDataObservableList);

        /**
         * Set up Choice Box
         */

        nameChoice.itemsProperty().set(nameList);
        nameChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            /**
             * This method needs to be provided by an implementation of
             * {@code ChangeListener}. It is called if the value of an
             * {@link ObservableValue} changes.
             * <p>
             * In general is is considered bad practice to modify the observed value in
             * this method.
             *
             * @param observable The {@code ObservableValue} which value changed
             * @param oldValue   The old value
             * @param newValue
             */
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                nameString = newValue;
                Platform.runLater(() -> {
                    nameLabel.setText(nameString);
                });

            }
        });
//
//
        portChoice.itemsProperty().set(portList);
        portChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                port = newValue.intValue();

            }
        });

        /**
         * Button
         */
        startButton.setOnAction(event -> {
            if (serverRunningFlag == false) {
                try {
                    if (port > 1000) {
                        serverSocket = new ServerSocket(port);
                        socketServerThread = new Thread(() -> {
                            while (serverSocket != null) {
                                try {
                                    Socket client = serverSocket.accept();
                                        HandlerThread ht = new HandlerThread(client);
                                        new Thread(ht).start();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        socketServerThread.start();

                    } else {
                        System.out.println("port is :" + port);
                    }


                } catch (Exception e) {

                }
            } else {
                new Alert(Alert.AlertType.WARNING, "server is running now").show();

            }


        });


    }

    /**
     * Set name of directory.
     *
     * @param dirFile File of directory name.
     * @return
     */
    @Override
    public boolean setDirectoryFile(File dirFile) {

        return false;
    }

    /**
     * Start write to files.
     *
     * @return
     */
    @Override
    public boolean startWrite() {
        return false;
    }

    /**
     * Stop write to files.
     *
     * @return
     */
    @Override
    public boolean stopWrite() {
        return false;
    }

    class HandlerThread implements Runnable {
        private Socket socket;
        ArrayBlockingQueue<String> imu_file_queue; // queue
        ArrayBlockingQueue<String> ble_file_queue; // queue

        HandlerThread(Socket s) {
            this.socket = s;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
//                DataInputStream input = new DataInputStream(socket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                socket.
                StringBuffer sb = new StringBuffer();

                while (socket.isConnected()) {
                    if (reader.ready()) {

                        String s = reader.readLine();
                        System.out.println(s);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
