package vbb.models.logic_gates;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 2/4/15.
 */
public class ICLogicGateWrapper
{
    private List<Socket> inputSockets;
    private Socket outputSocket;
    private LogicGate logicGate;

    private ChangeListener<Boolean> voltageChangeListener;

    public ICLogicGateWrapper()
    {
        inputSockets = new ArrayList<Socket>();
        voltageChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (areInputSocketsVoltagesSet())
                {
                    Voltage outputVoltage = logicGate.getOutput(getInputVoltages());
                    outputSocket.setVoltage(outputVoltage);
                }
            }
        };
    }

    public void setLogicGate(LogicGate logicGate)
    {
        this.logicGate = logicGate;
    }

    public void addInputSocket(Socket socket)
    {
        inputSockets.add(socket);
        socket.getVoltage().addListener(voltageChangeListener);
    }

    public boolean areInputSocketsVoltagesSet()
    {
        for (Socket socket : inputSockets)
        {
            if (socket.getVoltage() == null)
                return false;
        }
        return true;
    }

    private Voltage[] getInputVoltages()
    {
        List<Voltage> voltages = new ArrayList<Voltage>();
        for (Socket socket : inputSockets)
        {
            voltages.add(socket.getVoltage());
        }

        return (Voltage[]) voltages.toArray();
    }
}
