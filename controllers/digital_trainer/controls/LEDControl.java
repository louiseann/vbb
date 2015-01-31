package vbb.controllers.digital_trainer.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import vbb.models.digital_trainer.LED;

import java.io.IOException;

/**
 * Created by owie on 1/29/15.
 */
public class LEDControl extends StackPane
{
    @FXML
    private Circle bulb;
    @FXML
    private Circle light;

    private LED led;

    public LEDControl()
    {
        String url = "/vbb/views/fxml/digital_trainer/custom_control/led.fxml";
        FXMLLoader ledControlLoader = new FXMLLoader(getClass().getResource(url));
        ledControlLoader.setRoot(this);
        ledControlLoader.setController(this);

        try {
            ledControlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        led = new LED();
        lightUp(false);
    }

    public LED getLed()
    {
        return led;
    }

    public void setLed(LED led)
    {
        this.led = led;
    }

    public void lightUp(boolean light)
    {
        this.light.setVisible(light);
    }
}
