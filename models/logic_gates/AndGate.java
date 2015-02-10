package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

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
        return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        if (!input1.equals(Voltage.NONE) && !input2.equals(Voltage.NONE))
        {
            if (input1.equals(Voltage.LOW) || input2.equals(Voltage.LOW))
                return Voltage.LOW;
            else
                return Voltage.HIGH;
        }
        else
            return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        if (inputs.length > 1)
        {
            Voltage output = Voltage.HIGH;
            for (Voltage input : inputs)
            {
                if (input.equals(Voltage.LOW))
                    output = Voltage.LOW;
                else if (input.equals(Voltage.NONE))
                    return Voltage.NONE;
            }

            return output;
        }
        else
            return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        if (inputList.size() > 1)
        {
            Voltage output = Voltage.HIGH;
            for (Voltage input : inputList)
            {
                if (input.equals(Voltage.LOW))
                    output = Voltage.LOW;
                else if (input.equals(Voltage.NONE))
                    return Voltage.NONE;
            }

            return output;
        }
        else
            return Voltage.NONE;
    }
}