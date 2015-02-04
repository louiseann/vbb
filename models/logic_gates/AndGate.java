package vbb.models.logic_gates;

import vbb.models.Voltage;

/**
 * Created by owie on 2/3/15.
 */
public final class AndGate implements LogicGate
{
    private static AndGate instance = new AndGate();

    private AndGate() {}

    public static AndGate getInstance()
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