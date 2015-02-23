package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.LogicGate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 2/6/15.
 */
public class MultipleInputGateWrapper extends GateWrapper
{
    private List<Socket> inputSockets;

    private static ChangeListener<Boolean> voltageChanged;

    public MultipleInputGateWrapper(LogicGate logicGate)
    {
        super(logicGate);
        inputSockets = new ArrayList<Socket>();
        voltageChanged = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                handleVoltageChanged();
            }
        };
    }

    @Override
    public void handleVoltageChanged()
    {
        if (getChip().isPowered())
        {
            if (areInputSocketsVoltagesSet())
            {
                Voltage output = getLogicGate().getOutput(getInputVoltages());
                getOutputSocket().run(output);
            }
            else if (isOneInputSocketVoltageSet())
            {
                for (Socket inputSocket : inputSockets)
                {
                    if (inputSocket.runningVoltage().equals(Voltage.NONE))
                        inputSocket.setRunningVoltage(Voltage.LOW);
                }
                Voltage output = getLogicGate().getOutput(getInputVoltages());
                getOutputSocket().run(output);
            }
            else
                getOutputSocket().run(Voltage.NONE);
        }
        else
            getOutputSocket().run(Voltage.NONE);
    }

    public void add(Socket socket)
    {
        socket.runningVoltageChanged().addListener(voltageChanged);
        inputSockets.add(socket);
    }

    public boolean areInputSocketsVoltagesSet()
    {
        boolean set = false;
        for (Socket socket : inputSockets)
        {
            set = true;
            if (socket.runningVoltage().equals(Voltage.NONE))
                return false;
        }
        return set;
    }

    public boolean isOneInputSocketVoltageSet()
    {
        for (Socket socket : inputSockets)
        {
            if (!socket.runningVoltage().equals(Voltage.NONE))
                return true;
        }
        return false;
    }

    private List<Voltage> getInputVoltages()
    {
        List<Voltage> voltages = new ArrayList<Voltage>();
        for (Socket socket : inputSockets)
        {
            voltages.add(socket.runningVoltage());
        }

        return voltages;
    }
}
