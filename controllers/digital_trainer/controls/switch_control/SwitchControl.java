package vbb.controllers.digital_trainer.controls.switch_control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import vbb.models.digital_trainer.Switch;

import java.io.IOException;

/**
 * Created by owie on 12/5/14.
 */
public class SwitchControl extends StackPane
{
    private Switch switchInstance;

    @FXML
    Rectangle toggleHandle;
    @FXML
    Rectangle box;

    public SwitchControl(String fxmlSourceUrl)
    {
        switchInstance = new Switch();

        FXMLLoader switchLoader = new FXMLLoader(getClass().getResource(fxmlSourceUrl));
        switchLoader.setRoot(this);
        switchLoader.setController(this);

        try {
            switchLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Switch getSwitchInstance()
    {
        return switchInstance;
    }

    public void toggleSwitch()
    {
        if(switchInstance.isOn())
        {
            double xPosition = toggleHandle.getLayoutX() - (toggleHandle.getLayoutX() - box.getLayoutX());
            moveToggleHandle(xPosition, 0);
            toggleHandle.setStyle("-fx-effect: dropshadow(gaussian, #000000, 2, 0, -1, 0);");
        }

        else
        {
            double xPosition = box.getLayoutX() - (box.getWidth() - toggleHandle.getWidth());
            moveToggleHandle(xPosition, 0);
            toggleHandle.setStyle("-fx-effect: dropshadow(gaussian, #000000, 2, 0, 1, 0);");
        }

        switchInstance.toggle();
    }

    private void moveToggleHandle(double x, double y)
    {
        toggleHandle.setTranslateX(x);
        toggleHandle.setTranslateY(y);
    }
}
