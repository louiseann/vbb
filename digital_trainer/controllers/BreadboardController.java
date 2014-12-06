package vbb.digital_trainer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
        System.out.print("hey!");
        colConnectedLeftGroup = new GridPane();
        addSocketHoles(colConnectedLeftGroup, 2, 64);
        addSocketHoles(colConnectedRightGroup, 2, 64);
        addSocketHoles(rowConnectedLeftGroup, 5, 64);
        addSocketHoles(rowConnectedRightGroup, 5, 64);
    }

    private void addSocketHoles(GridPane connectedGroup, int rows, int cols)
    {
        for(int x = 0; x < rows; x++)
        {
            for(int y = 0; y < cols; y++)
            {
                Rectangle socketHole = new Rectangle(7, 7, Color.BLACK);
                connectedGroup.add(socketHole, x, y);
            }
        }
    }
}