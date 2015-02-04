package vbb.models.logic_gates;

import vbb.models.Voltage;

/**
 * Created by owie on 2/3/15.
 */
public final class NotGate implements LogicGate
{
    private static NotGate instance = new NotGate();

    private NotGate() {}

    public static NotGate getInstance()
    {
        return instance;
    }

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
