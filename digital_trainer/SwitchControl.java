package vbb.digital_trainer;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by owie on 12/5/14.
 */
public class SwitchControl extends StackPane
{
    @FXML private Rectangle box;
    @FXML private Rectangle toggle;

    public SwitchControl()
    {
        FXMLLoader switchLoader = new FXMLLoader(getClass().getResource("/vbb/fxml/switch.fxml"));
        switchLoader.setRoot(this);
        switchLoader.setController(this);

        try {
            switchLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void toggleSwitch(MouseEvent event)
    {
        System.out.println("switch clicked!");
        //move toggle to left side of box
    }
}
