package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

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
        if (input.equals(Voltage.HIGH))
            return Voltage.LOW;
        else if (input.equals(Voltage.LOW))
            return Voltage.HIGH;
        else
            return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        return Voltage.NONE;
    }
}
