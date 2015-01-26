package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardSocketControl;

/**
 * Created by owie on 1/19/15.
 */
public class DigitalTrainerController
{
    @FXML
    private BreadboardControl breadboard;
    @FXML
    private PowerSupplyAreaController powerSupplyAreaController;
    @FXML
    private HBox powerSupplyArea;
    @FXML
    private DataSwitchesAreaController dataSwitchesAreaController;
    @FXML
    private HBox dataSwitchesArea;
    @FXML
    private LedDisplayAreaController ledDisplayAreaController;
    @FXML
    private GridPane ledDisplayArea;
    @FXML
    private Pane appliedTools;

    public BreadboardControl getBreadBoard()
    {
        return breadboard;
    }

    public void setBreadboardEventHandlers(EventHandler<MouseEvent> mouseClickedHandler,
                                           EventHandler<MouseEvent> mouseEnteredHandler,
                                           EventHandler<MouseEvent> mouseExitedHandler)
    {
        breadboard.setSocketsMouseClickedHandler(mouseClickedHandler);
        breadboard.setSocketsMouseEnteredHandler(mouseEnteredHandler);
        breadboard.setSocketsMouseExitedHandler(mouseExitedHandler);
    }

    public void setSocketsHandler(EventHandler<MouseEvent> mouseClickedHandler,
                                  EventHandler<MouseEvent> mouseEnteredHandler,
                                  EventHandler<MouseEvent> mouseExitedHandler)
    {
        powerSupplyAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        powerSupplyAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        powerSupplyAreaController.addSocketsOnExitedHandler(mouseExitedHandler);

        dataSwitchesAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        dataSwitchesAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        dataSwitchesAreaController.addSocketsOnExitedHandler(mouseExitedHandler);

        ledDisplayAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        ledDisplayAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        ledDisplayAreaController.addSocketsOnExitedHandler(mouseExitedHandler);
    }

    public void applyTool(Node applied)
    {
        appliedTools.getChildren().add(applied);
    }
}