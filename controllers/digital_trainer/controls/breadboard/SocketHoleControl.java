package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vbb.controllers.tools.ToolsController;

import java.io.IOException;

/**
 * Created by owie on 1/11/15.
 */
public class SocketHoleControl extends Pane
{
    @FXML
    private Rectangle holeBox;

    private int row, col;

    public SocketHoleControl()
    {
        loadFXML();
    }

    public SocketHoleControl(int row, int col)
    {
        this.row = row;
        this.col = col;

        loadFXML();
    }

    private void loadFXML()
    {
        FXMLLoader socketHoleLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/digital_trainer/custom_control/socket_hole.fxml"));
        socketHoleLoader.setRoot(this);
        socketHoleLoader.setController(this);

        try {
            socketHoleLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize()
    {
        holeBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = ToolsController.getCurrentTool().getClassificationClassName();
                if (toolClass.equals("IntegratedCircuit"))
                    holeBox.setStyle("-fx-stroke: red;");
                else if (toolClass.equals("Wire"))
                    holeBox.setStyle("-fx-stroke: red;");
            }
        });

        holeBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = ToolsController.getCurrentTool().getClassificationClassName();
                if (toolClass.equals("IntegratedCircuit"))
                    holeBox.setStyle("-fx-stroke: null;");
                else if (toolClass.equals("Wire"))
                    holeBox.setStyle("-fx-stroke: null;");
            }
        });

        holeBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = ToolsController.getCurrentTool().getClassificationClassName();
                if (toolClass.equals("IntegratedCircuit"))
                    holeBox.setFill(Color.RED);
            }
        });
    }

    public Rectangle getHoleBox()
    {
        return holeBox;
    }
}
