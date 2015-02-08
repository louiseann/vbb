package vbb.models.tools.electronic_component;

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

    public void setupPowerTerminals()
    {
        positiveTerminal = new Socket();
        negativeTerminal = new Socket();
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
        gnd.getEndPoint2().setControlConnected(this.getNegativeTerminal());

        return gnd;
    }

    public Pin createVccPin()
    {
        Pin vcc = new Pin();
        vcc.getEndPoint2().setControlConnected(this.getPositiveTerminal());

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
