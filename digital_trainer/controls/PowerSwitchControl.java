package vbb.digital_trainer.controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Created by owie on 12/6/14.
 */
public class PowerSwitchControl extends SwitchControl
{
    public PowerSwitchControl()
    {
        FXMLLoader powerSwitchLoader = new FXMLLoader(getClass().getResource("/vbb/fxml/custom_control/power_switch.fxml"));
        powerSwitchLoader.setRoot(this);
        powerSwitchLoader.setController(this);

        try {
            powerSwitchLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void toggleSwitch(MouseEvent event)
    {
        System.out.println("power switch clicked!");
        //move toggle to left side of box
    }
}
