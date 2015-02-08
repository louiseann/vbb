package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import vbb.controllers.digital_trainer.controls.LEDControl;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.switch_control.PowerSwitchControl;
import vbb.models.digital_trainer.*;
import vbb.models.digital_trainer.switches.Switch;

/**
 * Created by owie on 1/24/15.
 */
public class PowerSupplyAreaController
{
    @FXML
    private PowerSwitchControl powerSwitch;
    @FXML
    private SocketControl positiveSocket, negativeSocket;
    @FXML
    private LEDControl led;

    public Switch getPowerSwitch()
    {
        return powerSwitch.getSoul();
    }

    public void putSoulToControls(Switch powerSwitch, Socket positiveTerminal, Socket negativeTerminal,
                                  LED powerIndicatorLed)
    {
        this.powerSwitch.setSoul(powerSwitch);
        positiveSocket.setSoul(positiveTerminal);
        negativeSocket.setSoul(negativeTerminal);
        led.setSoul(powerIndicatorLed);
    }

    public void addPowerSwitchClickedHandler(EventHandler<MouseEvent> handler)
    {
        powerSwitch.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    public void addSocketsOnClickedHandler(EventHandler<MouseEvent> handler)
    {
        positiveSocket.addMouseClickedHandler(handler);
        negativeSocket.addMouseClickedHandler(handler);
    }

    public void addSocketsOnEnteredHandler(EventHandler<MouseEvent> handler)
    {
        positiveSocket.addMouseEnteredHandler(handler);
        negativeSocket.addMouseEnteredHandler(handler);
    }

    public void addSocketsOnExitedHandler(EventHandler<MouseEvent> handler)
    {
        positiveSocket.addMouseExitedHandler(handler);
        negativeSocket.addMouseExitedHandler(handler);
    }
}
