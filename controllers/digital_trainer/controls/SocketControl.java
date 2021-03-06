package vbb.controllers.digital_trainer.controls;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import vbb.models.digital_trainer.Socket;

import java.io.IOException;

/**
 * Created by owie on 11/29/14.
 */
public class SocketControl extends StackPane
{
    @FXML
    private Circle socketCircle;

    private Socket soul;

    public SocketControl()
    {
        String fxmlUrl = "/vbb/views/fxml/digital_trainer/custom_control/socket.fxml";
        FXMLLoader connectorLoader = new FXMLLoader(getClass().getResource(fxmlUrl));
        connectorLoader.setRoot(this);
        connectorLoader.setController(this);

        try {
            connectorLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Socket getSoul()
    {
        return soul;
    }

    public void setSoul(Socket soul)
    {
        this.soul = soul;
    }

    public void addMouseClickedHandler(EventHandler<MouseEvent> handler)
    {
        socketCircle.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    public void addMouseEnteredHandler(EventHandler<MouseEvent> handler)
    {
        socketCircle.addEventHandler(MouseEvent.MOUSE_ENTERED, handler);
    }

    public void addMouseExitedHandler(EventHandler<MouseEvent> handler)
    {
        socketCircle.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
    }
}
