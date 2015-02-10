package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

/**
 * Created by owie on 2/3/15.
 */
public final class XnorGate implements LogicGate
{
    private static XnorGate instance = new XnorGate();

    private XnorGate() {}

    public static XnorGate getInstance()
    {
        return instance;
    }

    @Override
    public Voltage getOutput(Voltage input)
    {
        return Voltage.NONE;
    }

    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        Voltage xorOutput = XorGate.getInstance().getOutput(input1, input2);

        return NotGate.getInstance().getOutput(xorOutput);
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        Voltage xorOutput = XorGate.getInstance().getOutput(inputs);

        return NotGate.getInstance().getOutput(xorOutput);
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        Voltage xorOutput = XorGate.getInstance().getOutput(inputList);

        return NotGate.getInstance().getOutput(xorOutput);
    }
}
