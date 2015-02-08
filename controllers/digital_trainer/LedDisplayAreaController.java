package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import vbb.controllers.digital_trainer.controls.LEDControl;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.models.digital_trainer.LED;
import vbb.models.digital_trainer.Socket;

import java.util.Map;
import java.util.Set;

/**
 * Created by owie on 1/21/15.
 */
public class LedDisplayAreaController
{
    @FXML
    private GridPane ledDisplayArea;

    public void createLedSocketPairControls(Map<LED, Socket> ledSocketPairSouls)
    {
        Set<LED> ledSouls = ledSocketPairSouls.keySet();
        int row = 0;
        for (LED ledSoul : ledSouls)
        {
            LEDControl led = new LEDControl();
            led.setSoul(ledSoul);

            Socket socketSoul = ledSocketPairSouls.get(ledSoul);
            SocketControl socket = new SocketControl();
            socket.setSoul(socketSoul);

            ledDisplayArea.add(socket, 0, row);
            ledDisplayArea.add(led, 1, row++);
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
