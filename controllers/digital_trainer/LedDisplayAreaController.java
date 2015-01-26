package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import vbb.controllers.digital_trainer.controls.SocketControl;

/**
 * Created by owie on 1/21/15.
 */
public class LedDisplayAreaController
{
    @FXML
    private SocketControl socket1, socket2, socket3, socket4, socket5, socket6, socket7, socket8;

    public void addSocketsOnClickedHandler(EventHandler<MouseEvent> handler)
    {
        socket1.addMouseClickedHandler(handler);
        socket2.addMouseClickedHandler(handler);
        socket3.addMouseClickedHandler(handler);
        socket4.addMouseClickedHandler(handler);
        socket5.addMouseClickedHandler(handler);
        socket6.addMouseClickedHandler(handler);
        socket7.addMouseClickedHandler(handler);
        socket8.addMouseClickedHandler(handler);
    }

    public void addSocketsOnEnteredHandler(EventHandler<MouseEvent> handler)
    {
        socket1.addMouseEnteredHandler(handler);
        socket2.addMouseEnteredHandler(handler);
        socket3.addMouseEnteredHandler(handler);
        socket4.addMouseEnteredHandler(handler);
        socket5.addMouseEnteredHandler(handler);
        socket6.addMouseEnteredHandler(handler);
        socket7.addMouseEnteredHandler(handler);
        socket8.addMouseEnteredHandler(handler);
    }

    public void addSocketsOnExitedHandler(EventHandler<MouseEvent> handler)
    {
        socket1.addMouseExitedHandler(handler);
        socket2.addMouseExitedHandler(handler);
        socket3.addMouseExitedHandler(handler);
        socket4.addMouseExitedHandler(handler);
        socket5.addMouseExitedHandler(handler);
        socket6.addMouseExitedHandler(handler);
        socket7.addMouseExitedHandler(handler);
        socket8.addMouseExitedHandler(handler);
    }
}
