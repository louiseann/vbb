package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;

/**
 * Created by owie on 1/19/15.
 */
public class DigitalTrainerController
{
    @FXML
    private BreadboardControl breadboard;
    @FXML
    private DataSwitchesAreaController dataSwitchesAreaController;
    @FXML
    private HBox dataSwitchesArea;
    @FXML
    private LEDDisplayAreaController ledDisplayAreaController;
    @FXML
    private GridPane ledDisplayArea;
    @FXML
    private Group appliedTools;

    private EventHandler<MouseEvent> onClickOnSocket;

    public EventHandler<MouseEvent> getOnClickOnSocket()
    {
        return onClickOnSocket;
    }

    public void setOnClickOnSocket(EventHandler<MouseEvent> onClickOnSocket)
    {
        this.onClickOnSocket = onClickOnSocket;
    }

    public BreadboardControl getBreadboard()
    {
        return breadboard;
    }

    public void applyTool(Node applied)
    {
        appliedTools.getChildren().add(applied);
    }

}