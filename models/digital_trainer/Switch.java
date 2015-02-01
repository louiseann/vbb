package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by owie on 12/14/14.
 */
public class Switch extends Control
{
    public final static boolean ON = true;
    public final static boolean OFF = false;

    private BooleanProperty state = new SimpleBooleanProperty();
    private List<Control> poweredControls;

    public Switch()
    {
        super();
        setState(OFF);
        poweredControls = new ArrayList<Control>();

        state.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                powerUpControls(newValue);
            }
        });
        powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                powerUpControls(newValue && getState());
            }
        });
    }

    public BooleanProperty state()
    {
        return state;
    }

    private void setState(boolean state)
    {
        this.state.set(state);
    }

    public boolean getState()
    {
        return state.get();
    }

    public void toggle()
    {
        setState(!getState());
    }

    public boolean isOn()
    {
        return getState();
    }

    public void addPoweredControls(Control ... controls)
    {
        poweredControls.addAll(Arrays.asList(controls));
    }

    public void powerUpControls(boolean highVoltage)
    {
        boolean voltage;
        if (this.isPowered())
            voltage = highVoltage;
        else
            voltage = Voltage.LOW && highVoltage;

        for (Control control : poweredControls)
        {
            control.powerUp(voltage);
        }
    }
}
