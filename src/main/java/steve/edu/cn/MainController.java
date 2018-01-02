package steve.edu.cn;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;

public class MainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridPane.setPrefSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight()); //didn't work
    }
}
