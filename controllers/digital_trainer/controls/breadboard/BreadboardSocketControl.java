package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import vbb.controllers.tools.ToolsController;

import java.io.IOException;

/**
 * Created by owie on 1/11/15.
 */
public class BreadboardSocketControl extends Pane
{
    @FXML
    private Rectangle holeBox;

    private int row, col;
    private boolean inColConnectedGroup, inRowConnectedGroup;
    private boolean inLeft, inRight;

    private EventHandler<MouseEvent> onClickedHandler;

    public BreadboardSocketControl()
    {
        loadFXML();
    }

    public BreadboardSocketControl(int row, int col)
    {
        this.row = row;
        this.col = col;

        loadFXML();
    }

    private void loadFXML()
    {
        FXMLLoader breadboardHoleLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/digital_trainer/custom_control/breadboard_socket.fxml"));
        breadboardHoleLoader.setRoot(this);
        breadboardHoleLoader.setController(this);

        try {
            breadboardHoleLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Rectangle getHoleBox()
    {
        return holeBox;
    }

    public void addMouseClickedHandler(EventHandler<MouseEvent> handler)
    {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    public void addMouseEnteredHandler(EventHandler<MouseEvent> handler)
    {
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, handler);
    }

    public void addMouseExitedHandler(EventHandler<MouseEvent> handler)
    {
        this.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setInColConnectedGroup(boolean inColConnectedGroup)
    {
        this.inColConnectedGroup = inColConnectedGroup;
        this.inRowConnectedGroup = !inColConnectedGroup;
    }

    public boolean isInColConnectedGroup()
    {
        return inColConnectedGroup;
    }

    public void setInRowConnectedGroup(boolean inRowConnectedGroup)
    {
        this.inRowConnectedGroup = inRowConnectedGroup;
        this.inColConnectedGroup = !inRowConnectedGroup;
    }

    public boolean isInRowConnectedGroup()
    {
        return inRowConnectedGroup;
    }

    public void setInLeft(boolean inLeft)
    {
        this.inLeft = inLeft;
        this.inRight = !inLeft;
    }

    public boolean isInLeft()
    {
        return inLeft;
    }

    public void setInRight(boolean inRight)
    {
        this.inRight = inRight;
        this.inLeft = !inRight;
    }
}
