package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Circuit;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.LogicGate;
import vbb.models.tools.electronic_component.IntegratedCircuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 2/4/15.
 */
public abstract class GateWrapper
{
    private Socket outputSocket;
    private LogicGate logicGate;

    private IntegratedCircuit chip;
    private Circuit circuit;

    public GateWrapper(LogicGate logicGate)
    {
        setLogicGate(logicGate);
    }

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

    public void setOutputSocket(Socket socket, Circuit circuit)
    {
        this.outputSocket = socket;
        this.circuit = circuit;

        outputSocket.runningVoltageChanged().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("output socket voltage changed");
                getCircuit().run(outputSocket.runningVoltage(), outputSocket);
            }
        });
    }

    public IntegratedCircuit getChip()
    {
        return chip;
    }

    public void setChip(IntegratedCircuit chip)
    {
        this.chip = chip;
    }

    private Circuit getCircuit()
    {
        return circuit;
    }
}
