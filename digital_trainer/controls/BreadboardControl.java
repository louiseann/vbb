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
        setRow(64);
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
        addColumnConnectedHoles(colConnectedLeftGroup);
        addColumnConnectedHoles(colConnectedRightGroup);
        addRowConnectedHoles(rowConnectedLeftGroup);
        addRowConnectedHoles(rowConnectedRightGroup);
    }

    private Rectangle createHole()
    {
        Rectangle socketHole = new Rectangle(7, 7);
        socketHole.setStyle("-fx-fill: #212121;");
        return socketHole;
    }

    private Rectangle createSpace()
    {
        Rectangle socketHole = new Rectangle(7, 7);
        socketHole.setStyle("-fx-fill: #ffffff;");
        return socketHole;
    }

    private void addColumnConnectedHoles(GridPane twoColGroup)
    {
        for(int x = 0; x < 64;)
        {
            if(x == 0 || x == 1 || x == getRow()/2 || x == getRow()-1)
            {
                for(int y = 0; y < 2; y++)
                {
                    twoColGroup.add(createSpace(), y, x);
                }
                x++;
            }

            else
            {
                for (int r = 0; r < 5; r++)
                {
                    for (int y = 0; y < 2; y++)
                    {
                        twoColGroup.add(createHole(), y, x);
                    }
                    x++;
                }
                for(int y = 0; y < 2; y++)
                {
                    twoColGroup.add(createSpace(), y, x);
                }
                x++;
            }
        }
    }

    private void addRowConnectedHoles(GridPane socketHolesGroup)
    {
        for(int x = 0; x < getRow(); x++)
        {
            for(int y = 0; y < 5; y++)
            {
                socketHolesGroup.add(createHole(), y, x);
            }
        }
    }
}
