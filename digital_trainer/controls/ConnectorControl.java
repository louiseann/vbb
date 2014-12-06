package vbb.digital_trainer.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Created by owie on 11/29/14.
 */
public class ConnectorControl extends StackPane
{
    public ConnectorControl()
    {
        FXMLLoader connectorLoader = new FXMLLoader(getClass().getResource("/vbb/fxml/custom_control/io_connector.fxml"));
        connectorLoader.setRoot(this);
        connectorLoader.setController(this);

        try {
            connectorLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML public void handleMouseClick(MouseEvent event)
    {
        System.out.println("Clicked!");
    }
}
