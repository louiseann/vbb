package vbb.digital_trainer.controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Created by owie on 12/6/14.
 */
public class DataSwitchControl extends SwitchControl
{
    public DataSwitchControl()
    {
        FXMLLoader dataSwitchLoader = new FXMLLoader(getClass().getResource("/vbb/fxml/custom_control/data_switch.fxml"));
        dataSwitchLoader.setRoot(this);
        dataSwitchLoader.setController(this);

        try {
            dataSwitchLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void toggleSwitch(MouseEvent event)
    {
        System.out.println("data switch clicked!");
        //move toggle to left side of box
    }
}
