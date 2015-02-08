package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Control;
import vbb.models.Voltage;

/**
 * Created by owie on 11/29/14.
 */
public class LED extends Control
{
    private BooleanProperty lighted;

    public LED()
    {
        super();
        lighted = new SimpleBooleanProperty(false);
        setInnerConnection(true, false);
        this.powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (runningVoltage().equals(Voltage.HIGH))
                    lightUp(true);
                else
                    lightUp(false);
            }
        });
    }

    public BooleanProperty lighted()
    {
        return lighted;
    }

    public void lightUp(boolean light)
    {
        lighted.set(light);
    }

    @Override
    public void run(Voltage voltage)
    {
        setRunningVoltage(voltage);
        if (voltage.equals(Voltage.HIGH))
            powerUp(true);
        else
            powerUp(false);
    }
}
