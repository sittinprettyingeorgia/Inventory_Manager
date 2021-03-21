package InventoryManagement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
/**FUTURE ENHANCEMENT::
 * One Enhancement that could increase the functionality of the program
 * would be the ability to add multiple duplicate parts with unique part id's
 * at the same time instead of the redundant process of adding duplicate parts individually.
 */
/** Main class has primary control of the program*/
public class Main extends Application {
    //JavaDocuments are located in JavaDox folder within InventoryManagement(MitchellBlake)/src
    public static void main(String[] args) {
        launch(args);
    }
    /**starts application*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("build.fxml"));
        root.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:5px");
        primaryStage.setScene(new Scene(root, 1000, 400));
        primaryStage.show();


    }


}
