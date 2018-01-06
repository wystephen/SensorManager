package steve.edu.cn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pers.steve.sensor.gui.SensorImuViewerController;
import pers.steve.sensor.gui.SensorUWBViewerController;

import java.io.IOException;

public class GUITest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            ScrollPane scrollPane = new ScrollPane();

            GridPane mainPane = new GridPane();

            scrollPane.setContent(mainPane);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


            // use loader to lode fxml, prepare to interaction with @code{controller}.
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                    getResource("./IMUViwerSimple.fxml"));
            FlowPane imuBox = loader.load();
            SensorImuViewerController imuViewerController = loader.
                    getController();

            FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().
                    getResource("./IMUViwerSimple.fxml"));
            FlowPane imuBox2 = loader2.load();
            SensorImuViewerController imuViewerController2 = loader2.getController();

            FXMLLoader uwbLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("./UWBViewerSimple.fxml"));
            FlowPane uwbBox = uwbLoader.load();
            SensorUWBViewerController uwbViewerController = uwbLoader.getController();


            mainPane.add(imuBox, 0, 0);
            mainPane.add(imuBox2, 0, 1);
            mainPane.add(uwbBox, 0, 2);


            Button addImuButton = new Button("addNewImu");
            addImuButton.setOnMouseClicked(event -> {
                try {
                    FlowPane timuBox2 = (FlowPane) FXMLLoader.load(getClass().getClassLoader().
                            getResource("./IMUViwerSimple.fxml")
                    );
                    mainPane.add(timuBox2, 0, 3);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
//            mainPane.add(addImuButton,0,1);

            root.getChildren().add(scrollPane);
            Scene scen = new Scene(root, 1800, 1000);

            primaryStage.setScene(scen);
            primaryStage.show();


            scrollPane.prefHeightProperty().bind(primaryStage.heightProperty());
            scrollPane.prefWidthProperty().bind(primaryStage.widthProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
