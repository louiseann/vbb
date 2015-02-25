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

    private BreadboardSocket soul;

    private int row, col;
    private boolean topGroupElement;

    public final double side;

    public BreadboardSocketControl()
    {
        loadFXML();
        side = holeBox.getWidth();
    }

    public BreadboardSocketControl(BreadboardSocket soul, int row, int col)
    {
        setSoul(soul);

        setRow(row);
        setCol(col);

        loadFXML();
        side = holeBox.getWidth();
    }

    private void loadFXML()
    {
        String fxmlSourceUrl = "/vbb/views/fxml/digital_trainer/custom_control/breadboard_socket.fxml";
        FXMLLoader breadboardHoleLoader = new FXMLLoader(getClass().getResource(fxmlSourceUrl));
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

    public BreadboardSocket getSoul()
    {
        return soul;
    }

    public void setSoul(BreadboardSocket soul)
    {
        this.soul = soul;
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

    public void setTopGroupElement(boolean topGroupElement)
    {
        this.topGroupElement = topGroupElement;
    }

    public boolean isTopGroupElement()
    {
        return topGroupElement;
    }
}
