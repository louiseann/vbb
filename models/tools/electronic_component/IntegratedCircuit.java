package vbb.models.tools.electronic_component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.LogicGate;
import vbb.models.tools.connectors.Pin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 12/20/14.
 */
public class IntegratedCircuit extends ElectronicComponent
{
    private int pinCount;
    private LogicGate logicGate;
    private List<Pin> pins;

    private Socket positiveTerminal;
    private Socket negativeTerminal;

    public IntegratedCircuit(int pinCount, int colSpan)
    {
        super(pinCount /2, colSpan);
        this.setOverpassesBreadboardRavine(true);

        this.pinCount = pinCount;
        this.pins = new ArrayList<Pin>(pinCount);

        setupPowerTerminals();
    }

    public IntegratedCircuit(LogicGate logicGate)
    {
        super();
        setLogicGate(logicGate);
    }

    public int getPinCount()
    {
        return pinCount;
    }

    public LogicGate getLogicGate()
    {
        return logicGate;
    }

    public void setLogicGate(LogicGate logicGate)
    {
        this.logicGate = logicGate;
    }

    public void set(int index, Pin pin)
    {
        pins.add(index, pin);
    }

    public Pin getPin(int index)
    {
        return pins.get(index);
    }

    public void setupPowerTerminals()
    {
        positiveTerminal = new Socket();
        positiveTerminal.setPowerTrigger(Voltage.HIGH);
        negativeTerminal = new Socket();
        negativeTerminal.setPowerTrigger(Voltage.LOW);

        ChangeListener<Boolean> terminalPowerListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (positiveTerminal.runningVoltage().equals(Voltage.HIGH) &&
                        negativeTerminal.runningVoltage().equals(Voltage.LOW))
                    powerUp(true);
                else
                    powerUp(false);
            }
        };

        positiveTerminal.powered().addListener(terminalPowerListener);
        negativeTerminal.powered().addListener(terminalPowerListener);
    }

    public Socket getPositiveTerminal()
    {
        return positiveTerminal;
    }

    public Socket getNegativeTerminal()
    {
        return negativeTerminal;
    }

    public Pin createGndPin()
    {
        Pin gnd = new Pin();
        gnd.getSenderEndPoint().setControlConnected(this.getNegativeTerminal());

        return gnd;
    }

    public Pin createVccPin()
    {
        Pin vcc = new Pin();
        vcc.getSenderEndPoint().setControlConnected(this.getPositiveTerminal());

        return vcc;
    }

    public Pin createInputPin()
    {
        Pin input = new Pin();
        input.getSenderEndPoint().setControlConnected(new Socket());

        return input;
    }

    public Pin createOutputPin()
    {
        Pin output = new Pin();
        output.getReceiverEndPoint().setControlConnected(new Socket());

        return output;
    }
}
