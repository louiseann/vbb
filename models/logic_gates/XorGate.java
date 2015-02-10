package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

/**
 * Created by owie on 2/3/15.
 */
public final class XorGate implements LogicGate
{
    private static XorGate instance = new XorGate();

    private XorGate() {}

    public static XorGate getInstance()
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
            if (!input1.equals(input2))
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
            Voltage prevInput = Voltage.NONE;
            for (int i = 0; i < inputs.length; i++)
            {
                if (i == 0)
                    prevInput = inputs[0];
                else
                {
                    if (inputs[i].equals(Voltage.NONE))
                        return Voltage.NONE;
                    else if (inputs[i].equals(prevInput))
                        return Voltage.LOW;
                }
            }

            return Voltage.HIGH;
        }
        else
            return Voltage.NONE;
    }

    @Override
    public Voltage getOutput(List<Voltage> inputList)
    {
        if (inputList.size() > 1)
        {
            Voltage prevInput = Voltage.NONE;
            for (int i = 0; i < inputList.size(); i++)
            {
                if (i == 0)
                    prevInput = inputList.get(0);
                else
                {
                    if (inputList.get(i).equals(Voltage.NONE))
                        return Voltage.NONE;
                    else if (inputList.get(i).equals(prevInput))
                        return Voltage.LOW;
                }
            }

            return Voltage.HIGH;
        }
        else
            return Voltage.NONE;
    }
}
