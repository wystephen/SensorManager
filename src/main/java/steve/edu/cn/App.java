package steve.edu.cn;

import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import pers.steve.Sensors.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.net.URL;

import java.io.*;
import java.util.concurrent.SynchronousQueue;
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
                            si.addListener(new SEL());
                            si.StartInterface();
                        }
                    }
            );


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class SEL implements SensorDataListener<SensorOriginalStringEvent>{

        @Override
        public void SensorDataEvent(SensorOriginalStringEvent event) {
            System.out.print("Recieved data");
            System.out.println((event.getStringData()));
        }
    }


    public static void main(String[] args)
    {
        ExampleShow t_example = new ExampleShow();
        System.out.print(t_example.retureString());

        launch(args);
    }
}

