package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.switch_control.DataSwitchControl;
import vbb.controllers.digital_trainer.controls.switch_control.SwitchControl;
import vbb.models.digital_trainer.switches.DataSwitch;
import vbb.models.digital_trainer.Socket;

import java.util.Map;
import java.util.Set;

/**
 * Created by owie on 1/21/15.
 */
public class DataSwitchesAreaController
{
    @FXML
    private HBox socketsWrapper;
    @FXML
    private HBox dataSwitchesWrapper;

    public void createSwitchSocketPairControls(Map<DataSwitch, Socket> switchSocketPairSouls)
    {
        Set<DataSwitch> switchSouls = switchSocketPairSouls.keySet();
        for (DataSwitch switchSoul : switchSouls)
        {
            SwitchControl dataSwitchControl = new DataSwitchControl();
            dataSwitchControl.setSoul(switchSoul);

            Socket socketSoul = switchSocketPairSouls.get(switchSoul);
            SocketControl socket = new SocketControl();
            socket.setSoul(socketSoul);

            dataSwitchesWrapper.getChildren().add(dataSwitchControl);
            socketsWrapper.getChildren().add(socket);
        }
    }

    public void addSocketsOnClickedHandler(EventHandler<MouseEvent> handler)
    {
        for (Node socketNode : socketsWrapper.getChildren())
        {
            SocketControl socket = (SocketControl) socketNode;
            socket.addMouseClickedHandler(handler);
        }
    }

    public void addSocketsOnEnteredHandler(EventHandler<MouseEvent> handler)
    {
        for (Node socketNode : socketsWrapper.getChildren())
        {
            SocketControl socket = (SocketControl) socketNode;
            socket.addMouseEnteredHandler(handler);
        }
    }

    public void addSocketsOnExitedHandler(EventHandler<MouseEvent> handler)
    {
        for (Node socketNode : socketsWrapper.getChildren())
        {
            SocketControl socket = (SocketControl) socketNode;
            socket.addMouseExitedHandler(handler);
        }
    }

    public void addDataSwitchesOnClickedHandler(EventHandler<MouseEvent> handler)
    {
        for (Node switchNode : dataSwitchesWrapper.getChildren())
        {
            DataSwitchControl dataSwitch = (DataSwitchControl) switchNode;
            dataSwitch.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        }
    }
}
