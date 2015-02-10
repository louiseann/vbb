package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

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
        return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        Voltage orOutput = OrGate.getInstance().getOutput(input1, input2);

        return NotGate.getInstance().getOutput(orOutput);
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        Voltage orOutput = OrGate.getInstance().getOutput(inputs);

        return NotGate.getInstance().getOutput(orOutput);
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        Voltage orOutput = OrGate.getInstance().getOutput(inputList);

        return NotGate.getInstance().getOutput(orOutput);
    }
}
