package vbb.controllers.digital_trainer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.switch_control.DataSwitchControl;
import vbb.models.digital_trainer.Control;
import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.Switch;

import java.util.List;

/**
 * Created by owie on 1/21/15.
 */
public class DataSwitchesAreaController
{
    @FXML
    private VBox socketsWrapper;
    @FXML
    private VBox dataSwitchesWrapper;

    @FXML
    public void initialize()
    {
        addSwitchesPoweredControl();
    }

    public void addSwitchesPoweredControl()
    {
        List<Node> switchControls = dataSwitchesWrapper.getChildren();
        List<Node> socketControls = socketsWrapper.getChildren();
        for (int i = 0; i < switchControls.size() && i < socketControls.size(); i++)
        {
            Switch dataSwitch = ((DataSwitchControl) switchControls.get(i)).getSwitchInstance();
            Socket socket = ((SocketControl) socketControls.get(i)).getSocket();
            dataSwitch.addPoweredControls(socket);
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

    public void powerUpSwitches(boolean highVoltage)
    {
        for (Node switchNode : dataSwitchesWrapper.getChildren())
        {
            Switch dataSwitch = ((DataSwitchControl) switchNode).getSwitchInstance();
            dataSwitch.powerUp(highVoltage);
        }
    }
}
