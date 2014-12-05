package vbb.digital_trainer;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Map;

/**
 * Created by owie on 11/29/14.
 */
public class BreadboardController
{
    @FXML private GridPane colConnectedLeftGroup;
    @FXML private GridPane rowConnectedLeftGroup;
    @FXML private GridPane colConnectedRightGroup;
    @FXML private GridPane rowConnectedRightGroup;

    public void createSocketHoles()
    {
        addSocketHoles(colConnectedLeftGroup, 2, 64);
        addSocketHoles(colConnectedRightGroup, 2, 64);
        addSocketHoles(rowConnectedLeftGroup, 5, 64);
        addSocketHoles(rowConnectedRightGroup, 5, 64);
    }

    private void addSocketHoles(GridPane colConnectedGroup, int row, int col)
    {
        for(int x = 0; x < row; x++)
        {
            for(int y = 0; y < col; y++)
            {
                Rectangle socketHole = new Rectangle(7, 7, Color.BLACK);
                colConnectedGroup.add(socketHole, y, x);
            }
        }
    }
}