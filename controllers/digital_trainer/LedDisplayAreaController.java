package vbb.controllers.digital_trainer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import vbb.controllers.digital_trainer.controls.LEDControl;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.models.digital_trainer.Control;
import vbb.models.digital_trainer.LED;
import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 1/21/15.
 */
public class LedDisplayAreaController
{
    @FXML
    private GridPane ledDisplayArea;

    @FXML
    public void initialize()
    {
        connectTerminalsToLEDs();
    }

    private void connectTerminalsToLEDs()
    {
        Node socketNode = null;
        Node ledNode = null;
        for (Node node : ledDisplayArea.getChildren())
        {
            if (GridPane.getColumnIndex(node) == 0)
                socketNode = node;
            else if (GridPane.getColumnIndex(node) == 1)
                ledNode = node;

            if (socketNode != null && ledNode != null &&
                GridPane.getRowIndex(socketNode).equals(GridPane.getRowIndex(ledNode)))
            {
                Socket socket = ((SocketControl) socketNode).getSocket();
                final LED led = ((LEDControl) ledNode).getLed();
                socket.powered().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        led.powerUp(newValue);
                    }
                });
            }
        }
    }

    public void addSocketsOnClickedHandler(EventHandler<MouseEvent> handler)
    {
        for (Node nodeSocket : ledDisplayArea.getChildren())
        {
            if (GridPane.getColumnIndex(nodeSocket) == 0)
            {
                SocketControl socket = (SocketControl) nodeSocket;
                socket.addMouseClickedHandler(handler);
            }
        }
    }

    public void addSocketsOnEnteredHandler(EventHandler<MouseEvent> handler)
    {
        for (Node nodeSocket : ledDisplayArea.getChildren())
        {
            if (GridPane.getColumnIndex(nodeSocket) == 0)
            {
                SocketControl socket = (SocketControl) nodeSocket;
                socket.addMouseEnteredHandler(handler);
            }
        }
    }

    public void addSocketsOnExitedHandler(EventHandler<MouseEvent> handler)
    {
        for (Node nodeSocket : ledDisplayArea.getChildren())
        {
            if (GridPane.getColumnIndex(nodeSocket) == 0)
            {
                SocketControl socket = (SocketControl) nodeSocket;
                socket.addMouseExitedHandler(handler);
            }
        }
    }
}
