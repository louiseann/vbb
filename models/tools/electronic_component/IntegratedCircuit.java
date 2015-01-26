package vbb.models.tools.electronic_component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 12/20/14.
 */
public class IntegratedCircuit extends ElectronicComponent
{
    private int pins;
    private String gate;
    private List<String> pinsFunctions;

    public IntegratedCircuit()
    {
        super();
        this.setOverpassesBreadboardRavine(true);
    }

    public IntegratedCircuit(int pins)
    {
        super();
        this.setOverpassesBreadboardRavine(true);

        this.pins = pins;
        this.pinsFunctions = new ArrayList<String>(pins);
    }

    public IntegratedCircuit(int pins, int colSpan)
    {
        super(pins/2, colSpan);
        this.setOverpassesBreadboardRavine(true);

        this.pins = pins;
        this.pinsFunctions = new ArrayList<String>(pins);
    }

    public int getPins()
    {
        return pins;
    }

    public void setPins(int pins)
    {
        this.pins = pins;
    }

    public String getGate()
    {
        return gate;
    }

    public void setGate(String gate)
    {
        this.gate = gate;
    }

    public void set(int pin, String function)
    {
        pinsFunctions.add(pin, function);
    }
}
