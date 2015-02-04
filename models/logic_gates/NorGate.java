package vbb.models.logic_gates;

import vbb.models.Voltage;

/**
 * Created by owie on 2/3/15.
 */
public final class NorGate implements LogicGate
{
    private static NorGate instance = new NorGate();

    private NorGate() {}

    public static NorGate getInstance()
    {
        return instance;
    }

    @Override
    public Voltage getOutput(Voltage input)
    {
        return null;
    }

    @Override
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
