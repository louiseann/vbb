package vbb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vbb.digital_trainer.BreadboardController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/output_connectors_area.fxml"));
        primaryStage.setTitle("Hello World");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println(primaryScreenBounds.getWidth() + " " + primaryScreenBounds.getHeight());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
