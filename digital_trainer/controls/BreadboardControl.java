package vbb.digital_trainer.controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by owie on 12/6/14.
 */
public class BreadboardControl extends HBox
{
    @FXML
    private GridPane colConnectedLeftGroup;
    @FXML
    private GridPane rowConnectedLeftGroup;
    @FXML
    private GridPane colConnectedRightGroup;
    @FXML
    private GridPane rowConnectedRightGroup;

    private IntegerProperty width = new SimpleIntegerProperty();
    private IntegerProperty height = new SimpleIntegerProperty();
    private IntegerProperty row = new SimpleIntegerProperty();

    public BreadboardControl()
    {
        FXMLLoader breadboardLoader = new FXMLLoader(getClass().getResource("/vbb/fxml/custom_control/breadboard.fxml"));
        breadboardLoader.setRoot(this);
        breadboardLoader.setController(this);

        try {
            breadboardLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize()
    {
        createSocketHoles();
    }

    public void setRow(int row)
    {
        this.row.set(row);
    }

    public IntegerProperty rowProperty()
    {
        return row;
    }

    public int getRow()
    {
        return row.get();
    }

    private void createSocketHoles()
    {
        addSocketHole(colConnectedLeftGroup, 2);
        addSocketHole(colConnectedRightGroup, 2);
        addSocketHole(rowConnectedLeftGroup, 5);
        addSocketHole(rowConnectedRightGroup, 5);
    }

    private void addSocketHole(GridPane socketHolesGroup, int cols)
    {
        for(int x = 0; x < 64; x++)
        {
            for(int y = 0; y < cols; y++)
            {
                Rectangle socketHole = new Rectangle(7, 7, Color.BLACK);
                socketHolesGroup.add(socketHole, y, x);
            }
        }
    }
}
