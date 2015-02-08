package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.LogicGate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 2/4/15.
 */
public abstract class GateWrapper
{
    private Socket outputSocket;
    private LogicGate logicGate;

    public LogicGate getLogicGate()
    {
        return logicGate;
    }

    public void setLogicGate(LogicGate logicGate)
    {
        this.logicGate = logicGate;
    }

    public Socket getOutputSocket()
    {
        return outputSocket;
    }

    public void setOutputSocket(Socket socket)
    {
        this.outputSocket = socket;
    }
}
