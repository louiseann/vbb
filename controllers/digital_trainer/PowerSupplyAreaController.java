package vbb.controllers.digital_trainer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import vbb.controllers.digital_trainer.controls.LEDControl;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.switch_control.PowerSwitchControl;
import vbb.models.digital_trainer.*;

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
    private LEDControl ledControl;

    @FXML
    public void initialize()
    {
        powerSwitch.getSwitchInstance().powerUp(true);

        ledControl.getLed().powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ledControl.lightUp(newValue);
            }
        });
    }

    public Switch getPowerSwitch()
    {
        return powerSwitch.getSwitchInstance();
    }

    public void addPowerSwitchClickedHadler(EventHandler<MouseEvent> handler)
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

    public void powerUpControls(boolean highVoltage)
    {
        ledControl.getLed().powerUp(highVoltage);
        positiveSocket.getSocket().powerUp(highVoltage);
        negativeSocket.getSocket().powerUp(highVoltage && Voltage.LOW);
    }
}
