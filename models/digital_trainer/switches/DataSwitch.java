package vbb.models.digital_trainer.switches;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Circuit;
import vbb.models.Voltage;

/**
 * Created by owie on 2/7/15.
 */
public class DataSwitch extends Switch
{
    private DataSwitch instance;
    private Circuit circuit;

    public DataSwitch(Circuit circuit)
    {
        instance = this;
        this.circuit = circuit;

        powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                changeRunningVoltage();
            }
        });

        state().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                changeRunningVoltage();
            }
        });
    }

    private Circuit getCircuit()
    {
        return circuit;
    }

    private void changeRunningVoltage()
    {
        if (isPowered())
        {
            if (isOn())
                setRunningVoltage(Voltage.HIGH);
            else
                setRunningVoltage(Voltage.LOW);
        }
        else
            setRunningVoltage(Voltage.NONE);
        getCircuit().run(runningVoltage(), instance);
    }
}
