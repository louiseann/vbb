package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 2/6/15.
 */
public class MultipleInputGateWrapper extends GateWrapper
{
    private List<Socket> inputSockets;

    private static ChangeListener<Number> voltageListener;

    public MultipleInputGateWrapper()
    {
        super();
        inputSockets = new ArrayList<Socket>();
        voltageListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (areInputSocketsVoltagesSet()) {
                    Voltage outputVoltage = getLogicGate().getOutput(getInputVoltages());
                    getOutputSocket().setRunningVoltage(outputVoltage);
                }
            }
        };
    }

    public void add(Socket... sockets)
    {
        for (Socket socket : sockets)
        {
            inputSockets.add(socket);
        }
    }

    public List<Socket> getInputSockets()
    {
        return inputSockets;
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

    private Voltage[] getInputVoltages()
    {
        List<Voltage> voltages = new ArrayList<Voltage>();
        for (Socket socket : inputSockets)
        {
            voltages.add(socket.runningVoltage());
        }

        return (Voltage[]) voltages.toArray();
    }
}
