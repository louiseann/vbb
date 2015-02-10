package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

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
        return Voltage.NONE;
    }

    public Voltage getOutput(Voltage input1, Voltage input2)
    {
        if (!input1.equals(Voltage.NONE) && !input2.equals(Voltage.NONE))
        {
            if (input1.equals(Voltage.HIGH) || input2.equals(Voltage.HIGH))
                return Voltage.HIGH;
            else
                return Voltage.LOW;
        }
        else
            return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(Voltage... inputs)
    {
        if (inputs.length > 1)
        {
            Voltage output = Voltage.LOW;
            for (Voltage input : inputs)
            {
                if (input.equals(Voltage.HIGH))
                    output = Voltage.HIGH;
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
            Voltage output = Voltage.LOW;
            for (Voltage input : inputList)
            {
                if (input.equals(Voltage.HIGH))
                    output = Voltage.HIGH;
                else if (input.equals(Voltage.NONE))
                    return Voltage.NONE;
            }

            return output;
        }
        else
            return Voltage.NONE;
    }
}
