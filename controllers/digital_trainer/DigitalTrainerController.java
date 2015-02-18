package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;
import vbb.controllers.tools.controls.IntegratedCircuitControl;
import vbb.controllers.tools.controls.WireControl;
import vbb.models.connection.connector.Connector;
import vbb.models.digital_trainer.DigitalTrainer;
import vbb.models.digital_trainer.Socket;
import vbb.models.tools.connectors.Pin;
import vbb.models.tools.connectors.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by owie on 1/19/15.
 */
public class DigitalTrainerController
{
    @FXML
    private Pane digitalTrainerArea;

    @FXML
    private HBox board;

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

    private DigitalTrainer soul;

    private EventHandler<MouseEvent> enteredOnPluggedToolHandler;
    private EventHandler<MouseEvent> exitedOnPluggedToolHandler;

    private EventHandler<MouseEvent> clickedOnWireHandler;
    private EventHandler<MouseEvent> clickedOnChipHandler;

    private Map<Integer, Boolean> chip_zIndex;
    private int wire_zIndex;

    @FXML
    public void initialize()
    {
        soul = DigitalTrainer.getInstance();

        powerSupplyAreaController.putSoulToControls(soul.getPowerSwitch(), soul.getPositiveTerminal(),
                                                    soul.getNegativeTerminal(), soul.getPowerIndicatorLED());

        dataSwitchesAreaController.createSwitchSocketPairControls(soul.getSwitchTerminalPairs());

        ledDisplayAreaController.createLedSocketPairControls(soul.getLedTerminalPairs());

        breadboard.setSoul(soul.getBreadboard());

        initializeChipZIndex();
        wire_zIndex = 10;
    }

    public DigitalTrainer getSoul()
    {
        return soul;
    }

    public HBox getBoard()
    {
        return board;
    }

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

    public void setPowerSwitchClickedHandler(EventHandler<MouseEvent> handler)
    {
        powerSupplyAreaController.addPowerSwitchClickedHandler(handler);
    }

    public void setDataSwitchesClickedHandler(EventHandler<MouseEvent> handler)
    {
        dataSwitchesAreaController.addDataSwitchesOnClickedHandler(handler);
    }

    public void setEnteredOnPluggedToolHandler(EventHandler<MouseEvent> handler)
    {
        enteredOnPluggedToolHandler = handler;
    }

    public void setExitedOnPluggedToolHandler(EventHandler<MouseEvent> handler)
    {
        exitedOnPluggedToolHandler = handler;
    }

    public void setClickedOnWireHandler(EventHandler<MouseEvent> handler)
    {
        clickedOnWireHandler = handler;
    }

    public void setClickedOnChipHandler(EventHandler<MouseEvent> handler)
    {
        clickedOnChipHandler = handler;
    }

    public void plugChip(Node chip)
    {
        addOnHoverHandlers(chip);
        chip.addEventHandler(MouseEvent.MOUSE_CLICKED, clickedOnChipHandler);
        int zIndex = getUnoccupiedChipZIndex();
        digitalTrainerArea.getChildren().add(zIndex, chip);
        chip_zIndex.put(zIndex, true);
    }

    public void plugWire(Node wire)
    {
        addOnHoverHandlers(wire);
        wire.addEventHandler(MouseEvent.MOUSE_CLICKED, clickedOnWireHandler);
        digitalTrainerArea.getChildren().add(wire_zIndex++, wire);
    }

    public void unplugWire(Node wire)
    {
        digitalTrainerArea.getChildren().remove(wire);
    }

    private void initializeChipZIndex()
    {
        chip_zIndex = new LinkedHashMap<Integer, Boolean>(9);
        for (int i = 1; i < 10; i++)
        {
            chip_zIndex.put(i, false);
            digitalTrainerArea.getChildren().add(i, new ImageView());
        }
    }

    private int getUnoccupiedChipZIndex()
    {
        Set<Integer> zIndex = chip_zIndex.keySet();
        for (Integer z : zIndex)
        {
            if (!chip_zIndex.get(z))
                return z;
        }

        return -1;
    }

    private void addOnHoverHandlers(Node pluggedTool)
    {
        pluggedTool.addEventHandler(MouseEvent.MOUSE_ENTERED, enteredOnPluggedToolHandler);
        pluggedTool.addEventHandler(MouseEvent.MOUSE_EXITED, exitedOnPluggedToolHandler);
    }

    public void handleClickOnPluggedChip(IntegratedCircuitControl control)
    {
        IntegratedCircuit chip = control.getSoul();
        List<Pin> pins = chip.getPins();
        for (Pin pin : pins)
        {
            remove(pin);
        }

        int zIndex = digitalTrainerArea.getChildren().indexOf(control);
        digitalTrainerArea.getChildren().remove(control);

        chip_zIndex.put(zIndex, false);
        digitalTrainerArea.getChildren().add(zIndex, new IntegratedCircuitControl());
    }

    public void handleClickOnPluggedWire(WireControl control)
    {
        remove(control.getWire());

        digitalTrainerArea.getChildren().remove(control);
        wire_zIndex--;
    }

    private void remove(Connector connection)
    {
        soul.getCircuit().deleteConnection(connection);

        ((Socket) connection.getEndPoint1().getControlConnected()).setOccupied(false);
        ((Socket) connection.getEndPoint2().getControlConnected()).setOccupied(false);
    }
}