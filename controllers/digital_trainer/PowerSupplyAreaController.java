package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import vbb.controllers.digital_trainer.controls.SocketControl;

/**
 * Created by owie on 1/24/15.
 */
public class PowerSupplyAreaController
{
    @FXML
    private SocketControl positiveSocket, negativeSocket;

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
