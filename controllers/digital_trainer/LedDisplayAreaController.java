package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import vbb.controllers.digital_trainer.controls.LEDControl;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.models.digital_trainer.Control;

/**
 * Created by owie on 1/21/15.
 */
public class LedDisplayAreaController
{
    @FXML
    private GridPane ledDisplayArea;

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

    public void powerUpLEDs(boolean voltage)
    {
        for (Node ledNode : ledDisplayArea.getChildren())
        {
            if (GridPane.getColumnIndex(ledNode) == 1)
            {
                Control led = ((LEDControl) ledNode).getLed();
                led.powerUp(voltage);
            }
        }
    }
}
