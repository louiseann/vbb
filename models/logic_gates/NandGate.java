package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

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
    public Voltage getOutput(Voltage input)
    {
        return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        Voltage andOutput = AndGate.getInstance().getOutput(input1, input2);

        return NotGate.getInstance().getOutput(andOutput);
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        Voltage andOutput = AndGate.getInstance().getOutput(inputs);

        return NotGate.getInstance().getOutput(andOutput);
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        Voltage andOutput = AndGate.getInstance().getOutput(inputList);

        return NotGate.getInstance().getOutput(andOutput);
    }
}
