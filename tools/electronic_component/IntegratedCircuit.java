package vbb.tools.electronic_component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by owie on 12/20/14.
 */
public class IntegratedCircuit extends ElectronicComponent
{
    private int pinCount;
    private List<String> pins;

    public IntegratedCircuit()
    {
        super();
        this.overpassesBreadboardRavine = true;
    }

    public IntegratedCircuit(int pins)
    {
        super();
        this.overpassesBreadboardRavine = true;

        pinCount = pins;
        this.pins = new ArrayList<String>(pins);
    }

    public IntegratedCircuit(int pins, int colSpan)
    {
        super(pins/2, colSpan);
        this.overpassesBreadboardRavine = true;

        pinCount = pins;
    }

    public void set(int pin, String function)
    {
        pins.set(pin, function);
    }
}
