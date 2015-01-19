package vbb.models.tools.electronic_component;

/**
 * Created by owie on 1/14/15.
 */
public final class TTL74SeriesIC
{
    private final static int pins = 14;
    private final static int colsSpan = 2;

    public static IntegratedCircuit andGateChip()
    {
        IntegratedCircuit andGateChip = new IntegratedCircuit(pins, colsSpan);

        return andGateChip;
    }
}
