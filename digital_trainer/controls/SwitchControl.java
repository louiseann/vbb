package vbb.digital_trainer.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by owie on 12/5/14.
 */
public class SwitchControl extends StackPane
{
    @FXML private Rectangle box;
    @FXML private Rectangle toggle;

    @FXML
    public void toggleSwitch(MouseEvent event)
    {
        System.out.println("switch clicked!");
        //move toggle to left side of box
    }
}
