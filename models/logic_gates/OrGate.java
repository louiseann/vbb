package vbb.models.logic_gates;

import vbb.models.Voltage;

/**
 * Created by owie on 2/3/15.
 */
public final class OrGate implements LogicGate
{
    private static OrGate instance = new OrGate();

    private OrGate() {}

    public static OrGate getInstance()
    {
        return instance;
    }

    @Override
    public Voltage getOutput(Voltage input)
    {
        return null;
    }

    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        return null;
    }

    @Override
    public Voltage getOutput(Voltage... voltages)
    {
        return null;
    }
}
