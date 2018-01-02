package steve.edu.cn;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import pers.steve.Sensors.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
//import java.


public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            ;
            Parent root = FXMLLoader.load(getClass().getClassLoader().
                    getResource("./frame.fxml")
            );

            //primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


            Button btn = (Button) root.lookup("#buttonconnect");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {

                            System.out.println("clicked button");
                            SerialInterface si = new SerialInterface();
//                            si.addListener(new SEL());
//                            si.StartInterface();
                            SensorJY901 sj = new SensorJY901();
                            sj.setInterface(si);
                            sj.StartFileOutput(1);
                            sj.StartSensor(1);
                        }
                    }
            );


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class SEL implements SensorOriginalDataListener {

        @Override
        public void SensorDataEvent(SensorOriginalDataEvent event) {
            System.out.print("Recieved data");
            System.out.println(new String(event.get_bytes()));
        }
    }


    public static void main(String[] args)
    {
        ExampleShow t_example = new ExampleShow();
        System.out.print(t_example.retureString());

        launch(args);
    }
}

