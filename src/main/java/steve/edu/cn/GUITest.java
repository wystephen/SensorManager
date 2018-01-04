package steve.edu.cn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GUITest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            GridPane mainPane = new GridPane();
//            HBox imuBox = new HBox(new Button("info"));

            FlowPane imuBox =(FlowPane)  FXMLLoader.load(getClass().getClassLoader().
                    getResource("./IMUViwerSimple.fxml")
            );
            mainPane.add(imuBox, 0, 0);
            root.getChildren().add(mainPane);
            Scene scen = new Scene(root, imuBox.getMinWidth(), 1000);

            primaryStage.setScene(scen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
