package vbb.models.logic_gates;

import vbb.models.Voltage;

/**
 * Created by owie on 2/3/15.
 */
public final class NandGate implements LogicGate
{
    private static NandGate instance = new NandGate();

    private NandGate() {}

    public static NandGate getInstance()
    {
        return instance;
    }

    @Override
    public Voltage getOutput(Voltage input) {
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
