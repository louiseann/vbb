package vbb.controllers.digital_trainer.controls;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import vbb.controllers.tools.ToolsController;

import java.io.IOException;

/**
 * Created by owie on 11/29/14.
 */
public class ConnectorControl extends StackPane
{
    @FXML
    private Circle connectorCircle;

    public ConnectorControl()
    {
        FXMLLoader connectorLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/digital_trainer/custom_control/dt_board_connector.fxml"));
        connectorLoader.setRoot(this);
        connectorLoader.setController(this);

        try {
            connectorLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize()
    {
        connectorCircle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ToolsController.getCurrentTool().getClassificationClassName().equals("Wire"))
                    connectorCircle.setStyle("-fx-stroke: red;");
            }
        });

        connectorCircle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ToolsController.getCurrentTool().getClassificationClassName().equals("Wire"))
                    connectorCircle.setStyle("-fx-stroke: null;");
            }
        });
    }

    @FXML
    public void handleMouseClick(MouseEvent event)
    {
        if (ToolsController.getCurrentTool().getClassificationClassName().equals("Wire"))
        {
            System.out.println("Clicked!");
        }
    }
}
