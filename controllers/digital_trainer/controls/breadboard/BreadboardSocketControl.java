package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;

import java.io.IOException;

/**
 * Created by owie on 1/11/15.
 */
public class BreadboardSocketControl extends Pane
{
    @FXML
    private Rectangle holeBox;

    private BreadboardSocket socket;

    private int row, col;
    private boolean inColConnectedGroup, inRowConnectedGroup;
    private boolean inLeft, inRight;

    public BreadboardSocketControl()
    {
        loadFXML();
    }

    public BreadboardSocketControl(BreadboardSocket socket, int row, int col)
    {
        setSocket(socket);

        setRow(row);
        setCol(col);

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

    public BreadboardSocket getSocket()
    {
        return socket;
    }

    public void setSocket(BreadboardSocket socket)
    {
        this.socket = socket;
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
