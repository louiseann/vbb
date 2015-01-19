package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
        FXMLLoader breadboardLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/digital_trainer/custom_control/breadboard.fxml"));
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

    private SocketHoleControl createHole()
    {
        SocketHoleControl socketHole = new SocketHoleControl();
        socketHole.getHoleBox().setStyle("-fx-fill: #212121;");
        return socketHole;
    }

    private SocketHoleControl createSpace()
    {
        SocketHoleControl socketHole = new SocketHoleControl();
        socketHole.getHoleBox().setStyle("-fx-fill: #ffffff;");
        socketHole.getHoleBox().setDisable(true);
        return socketHole;
    }

    private void addColumnConnectedHoles(GridPane twoColGroup)
    {
        for(int x = 0; x < getRow();)
        {
            if(x == 0 || x == 1 || x == getRow()/2 || x == getRow()-1 || x == getRow()-2)
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
