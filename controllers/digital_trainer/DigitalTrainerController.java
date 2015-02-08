package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;
import vbb.models.digital_trainer.DigitalTrainer;

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
    private StackPane pluggedTools;
    @FXML
    private Pane pluggedChips;
    @FXML
    private Pane pluggedWires;

    private DigitalTrainer soul;

    private EventHandler<MouseEvent> enteredOnPluggedToolHandler;
    private EventHandler<MouseEvent> exitedOnPluggedToolHandler;

    @FXML
    public void initialize()
    {
        soul = DigitalTrainer.getInstance();

        powerSupplyAreaController.putSoulToControls(soul.getPowerSwitch(), soul.getPositiveTerminal(),
                                                    soul.getNegativeTerminal(), soul.getPowerIndicatorLED());

        dataSwitchesAreaController.createSwitchSocketPairControls(soul.getSwitchTerminalPairs());

        ledDisplayAreaController.createLedSocketPairControls(soul.getLedTerminalPairs());

        breadboard.setSoul(soul.getBreadboard());

        enteredOnPluggedToolHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("entered");
            }
        };
        exitedOnPluggedToolHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("exited");
            }
        };
    }

    public DigitalTrainer getSoul()
    {
        return soul;
    }

    public BreadboardControl getBreadBoard()
    {
        return breadboard;
    }

    public EventHandler<MouseEvent> getEnteredOnPluggedToolHandler()
    {
        return enteredOnPluggedToolHandler;
    }

    public EventHandler<MouseEvent> getExitedOnPluggedToolHandler()
    {
        return exitedOnPluggedToolHandler;
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

    public void setPowerSwitchClickedHandler(EventHandler<MouseEvent> handler)
    {
        powerSupplyAreaController.addPowerSwitchClickedHandler(handler);
    }

    public void setDataSwitchesClickedHandler(EventHandler<MouseEvent> handler)
    {
        dataSwitchesAreaController.addDataSwitchesOnClickedHandler(handler);
    }

    public void plugChip(Node chip)
    {
        pluggedChips.getChildren().add(chip);
    }

    public void plugWire(Node wire)
    {
        pluggedWires.getChildren().add(wire);
    }

    public void unplugWire(Node wire)
    {
        pluggedWires.getChildren().remove(wire);
    }
}