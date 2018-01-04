package pers.steve.sensor.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SensorImuViewerController implements Initializable {
    @FXML
    protected HBox mainPane;

    @FXML
    protected Button startButton;

    @FXML
    protected Button stopButton;

    @FXML
    protected Label nameLabel;


    @FXML
    protected ChoiceBox<String> deviceChoice;

    @FXML
    protected ChoiceBox<Integer> speedChoice;


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

    }
}
