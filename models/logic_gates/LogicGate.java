package vbb.models.logic_gates;

import vbb.models.Voltage;

import java.util.List;

/**
 * Created by owie on 2/3/15.
 */
public interface LogicGate
{
    public Voltage getOutput(Voltage input);
    public Voltage getOutput(Voltage input1, Voltage input2);
    public Voltage getOutput(Voltage... inputs);
    public Voltage getOutput(List<Voltage> inputList);
}
