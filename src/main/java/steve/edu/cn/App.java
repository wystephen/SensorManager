package steve.edu.cn;

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
                            System.out.print("clicked button");
                        }
                    }
            );


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        ExampleShow t_example = new ExampleShow();
        System.out.print(t_example.retureString());

        launch(args);
    }
}

